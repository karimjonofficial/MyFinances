package com.orka.myfinances.lib.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
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
fun DecimalTextField(
    value: Double?,
    onValueChange: (Double?) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    decimalPlaces: Int = 2
) {
    DecimalTextFieldImpl(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        decimalPlaces = decimalPlaces,
        outlined = false
    )
}

@Composable
fun OutlinedDecimalTextField(
    value: Double?,
    onValueChange: (Double?) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    decimalPlaces: Int = 2
) {
    DecimalTextFieldImpl(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        decimalPlaces = decimalPlaces,
        outlined = true
    )
}

@Composable
private fun DecimalTextFieldImpl(
    value: Double?,
    onValueChange: (Double?) -> Unit,
    modifier: Modifier,
    label: @Composable (() -> Unit)?,
    decimalPlaces: Int,
    outlined: Boolean
) {
    var rawText by remember {
        mutableStateOf(value?.toPlainString(decimalPlaces) ?: "")
    }

    LaunchedEffect(value) {
        if (rawText.toDoubleOrNull() != value) {
            rawText = value?.toPlainString(decimalPlaces) ?: ""
        }
    }

    val formatter = remember(decimalPlaces) {
        NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = decimalPlaces
            maximumFractionDigits = decimalPlaces
            isGroupingUsed = true
        }
    }

    val visualTransformation = remember(formatter) {
        DecimalVisualTransformation(formatter)
    }

    val onValueChangeInternal: (String) -> Unit = { input ->
        val sanitized = input
            .replace(",", "")
            .filter { it.isDigit() || it == '.' }

        if (sanitized == "0" && value == null) {
            rawText = ""
            onValueChange(null)
        } else {
            rawText = sanitized
            val parsed = sanitized.toDoubleOrNull()
            onValueChange(parsed)
        }
    }

    if (outlined) {
        OutlinedTextField(
            modifier = modifier,
            value = rawText,
            onValueChange = onValueChangeInternal,
            label = label,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = visualTransformation,
            singleLine = true
        )
    } else {
        TextField(
            modifier = modifier,
            value = rawText,
            onValueChange = onValueChangeInternal,
            label = label,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = visualTransformation,
            singleLine = true
        )
    }
}

private class DecimalVisualTransformation(
    private val formatter: NumberFormat
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text
        if (raw.isEmpty()) return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        val number = raw.toDoubleOrNull() ?: return TransformedText(AnnotatedString(raw), OffsetMapping.Identity)
        val formatted = formatter.format(number)
        return TransformedText(AnnotatedString(formatted), DecimalOffsetMapping(raw, formatted))
    }
}

private class DecimalOffsetMapping(
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

private fun Double.toPlainString(decimals: Int): String =
    "%.${decimals}f".format(Locale.US, this)

@Preview
@Composable
private fun DecimalTextFieldPreview() {
    ScaffoldPreview(title = "Decimal TextFields") { paddingValues ->
        Column(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val decimalValue = remember { mutableStateOf<Double?>(null) }

            DecimalTextField(
                value = decimalValue.value,
                onValueChange = { decimalValue.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { androidx.compose.material3.Text("Decimal") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedDecimalTextField(
                value = decimalValue.value,
                onValueChange = { decimalValue.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { androidx.compose.material3.Text("Outlined Decimal") }
            )
        }
    }
}
