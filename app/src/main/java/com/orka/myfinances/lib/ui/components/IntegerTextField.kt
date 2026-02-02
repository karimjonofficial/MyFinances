package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import java.text.NumberFormat
import java.util.Locale

@Composable
fun IntegerTextField(
    modifier: Modifier = Modifier,
    value: Int?,
    onValueChange: (Int?) -> Unit,
    label: (@Composable () -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    IntegerTextFieldImpl(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = leadingIcon,
        outlined = false
    )
}

@Composable
fun IntegerTextField(
    modifier: Modifier = Modifier,
    value: Int?,
    onValueChange: (Int?) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    IntegerTextFieldImpl(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        leadingIcon = leadingIcon,
        outlined = false
    )
}

@Composable
fun OutlinedIntegerTextField(
    value: Int?,
    onValueChange: (Int?) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null
) {
    IntegerTextFieldImpl(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        outlined = true
    )
}

@Composable
private fun IntegerTextFieldImpl(
    modifier: Modifier = Modifier,
    value: Int?,
    onValueChange: (Int?) -> Unit,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    outlined: Boolean
) {
    var rawText by remember {
        mutableStateOf(value?.toString() ?: "")
    }

    LaunchedEffect(value) {
        if (rawText.toIntOrNull() != value) {
            rawText = value?.toString() ?: ""
        }
    }

    val formatter = remember {
        NumberFormat.getIntegerInstance(Locale.US).apply {
            isGroupingUsed = true
        }
    }

    val visualTransformation = remember(formatter) {
        IntVisualTransformation(formatter)
    }

    val onValueChangeInternal: (String) -> Unit = { input ->
        val sanitized = input
            .replace(",", "")
            .filter { it.isDigit() }

        if (sanitized.isEmpty()) {
            rawText = ""
            onValueChange(null)
        } else if (sanitized == "0" && value == null) {
            rawText = ""
            onValueChange(null)
        } else {
            val parsedLong = sanitized.toLongOrNull()
            if (parsedLong != null && parsedLong <= Int.MAX_VALUE) {
                rawText = sanitized
                onValueChange(parsedLong.toInt())
            }
        }
    }

    if (outlined) {
        OutlinedTextField(
            modifier = modifier,
            value = rawText,
            onValueChange = onValueChangeInternal,
            label = label,
            leadingIcon = leadingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = visualTransformation,
            singleLine = true
        )
    } else {
        TextField(
            modifier = modifier,
            value = rawText,
            onValueChange = onValueChangeInternal,
            label = label,
            leadingIcon = leadingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = visualTransformation,
            singleLine = true
        )
    }
}

private class IntVisualTransformation(
    private val formatter: NumberFormat
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text
        if (raw.isEmpty()) return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        val number = raw.toLongOrNull() ?: return TransformedText(AnnotatedString(raw), OffsetMapping.Identity)
        val formatted = formatter.format(number)
        return TransformedText(AnnotatedString(formatted), IntegerOffsetMapping(raw, formatted))
    }
}

private class IntegerOffsetMapping(
    private val originalText: String,
    private val transformedText: String
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        var o = 0
        var t = 0
        while (o < offset && o < originalText.length && t < transformedText.length) {
            if (originalText[o] == transformedText[t]) o++
            t++
        }
        return t
    }
    override fun transformedToOriginal(offset: Int): Int {
        var o = 0
        var t = 0
        while (t < offset && t < transformedText.length) {
            if (o < originalText.length && originalText[o] == transformedText[t]) o++
            t++
        }
        return o
    }
}

@Preview
@Composable
private fun IntegerTextFieldPreview() {
    ScaffoldPreview(title = "Integer TextFields") { paddingValues ->
        Column(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val intValue = remember { mutableStateOf<Int?>(null) }

            IntegerTextField(
                value = intValue.value,
                onValueChange = { intValue.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Integer") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedIntegerTextField(
                value = intValue.value,
                onValueChange = { intValue.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Outlined Integer") }
            )
        }
    }
}
