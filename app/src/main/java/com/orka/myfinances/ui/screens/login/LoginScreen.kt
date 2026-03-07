package com.orka.myfinances.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    viewModel: LoginScreenViewModel
) {
    val username = rememberSaveable { mutableStateOf("admin") }
    val password = rememberSaveable { mutableStateOf("123") }
    val remember = rememberSaveable { mutableStateOf(false) }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    val isErrorState = state is LoginScreenState.Error
    val isLoadingState = state is LoginScreenState.Loading
    val textFieldError = isErrorState && !state.serverError

    Scaffold(modifier = modifier) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer
                        )
                    )
                )
                .scaffoldPadding(paddingValues)
        ) {
            val isWide = maxWidth >= 900.dp

            if (isWide) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(28.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    BrandPanel(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )

                    AuthPanel(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        state = state,
                        isErrorState = isErrorState,
                        isLoadingState = isLoadingState,
                        textFieldError = textFieldError,
                        username = username.value,
                        password = password.value,
                        remember = remember.value,
                        passwordVisible = passwordVisible.value,
                        onUsernameChange = { username.value = it },
                        onPasswordChange = { password.value = it },
                        onRememberChange = { remember.value = it },
                        onPasswordVisibleChange = { passwordVisible.value = !passwordVisible.value },
                        onSubmit = {
                            if (username.value.isNotBlank() && password.value.isNotBlank()) {
                                if (remember.value) {
                                    viewModel.authorizeAndRemember(username.value, password.value)
                                } else {
                                    viewModel.authorize(username.value, password.value)
                                }
                            }
                        }
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    BrandPanel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.45f)
                    )

                    AuthPanel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.55f),
                        state = state,
                        isErrorState = isErrorState,
                        isLoadingState = isLoadingState,
                        textFieldError = textFieldError,
                        username = username.value,
                        password = password.value,
                        remember = remember.value,
                        passwordVisible = passwordVisible.value,
                        onUsernameChange = { username.value = it },
                        onPasswordChange = { password.value = it },
                        onRememberChange = { remember.value = it },
                        onPasswordVisibleChange = { passwordVisible.value = !passwordVisible.value },
                        onSubmit = {
                            if (username.value.isNotBlank() && password.value.isNotBlank()) {
                                if (remember.value) {
                                    viewModel.authorizeAndRemember(username.value, password.value)
                                } else {
                                    viewModel.authorize(username.value, password.value)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BrandPanel(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(54.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = stringResource(R.string.logo)
                )
                HorizontalSpacer(12)
                Column {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Retail command center",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Track stock, sales, and debt from one place.",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Built for quick daily operations with less friction.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HighlightStat(label = "Live", value = "Inventory")
                HighlightStat(label = "Fast", value = "Checkout")
                HighlightStat(label = "Unified", value = "Clients")
            }
        }
    }
}

@Composable
private fun HighlightStat(label: String, value: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium)
            Text(text = value, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun AuthPanel(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    isErrorState: Boolean,
    isLoadingState: Boolean,
    textFieldError: Boolean,
    username: String,
    password: String,
    remember: Boolean,
    passwordVisible: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberChange: (Boolean) -> Unit,
    onPasswordVisibleChange: () -> Unit,
    onSubmit: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.Top
        ) {
            if (isErrorState) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(vertical = 8.dp, horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.error),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                    HorizontalSpacer(10)
                    Text(
                        text = (state as? LoginScreenState.Error)?.message?.str().orEmpty(),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2
                    )
                }
                VerticalSpacer(12)
            }

            Text(
                text = stringResource(R.string.enter_to_account),
                style = MaterialTheme.typography.headlineMedium
            )
            VerticalSpacer(10)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoadingState,
                isError = textFieldError,
                singleLine = true,
                value = username,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.person),
                        contentDescription = null
                    )
                },
                onValueChange = onUsernameChange,
                label = { Text(stringResource(R.string.username)) },
                supportingText = {
                    if (textFieldError) Text(stringResource(R.string.change_the_username))
                }
            )

            VerticalSpacer(8)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoadingState,
                isError = textFieldError,
                singleLine = true,
                value = password,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.lock),
                        contentDescription = null
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = onPasswordVisibleChange) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.visibility else R.drawable.visibility_off
                            ),
                            contentDescription = null
                        )
                    }
                },
                onValueChange = onPasswordChange,
                label = { Text(stringResource(R.string.password)) },
                supportingText = {
                    if (textFieldError) Text(stringResource(R.string.change_the_password))
                }
            )

            VerticalSpacer(6)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    enabled = !isLoadingState,
                    checked = remember,
                    onCheckedChange = onRememberChange
                )
                HorizontalSpacer(8)
                Text(stringResource(R.string.remember_me))
            }

            VerticalSpacer(8)
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = onSubmit,
                enabled = (username.isNotBlank() && password.isNotBlank()) && !isLoadingState
            ) {
                if (isLoadingState) {
                    CircularProgressIndicator(modifier = Modifier.size(22.dp))
                } else {
                    Text(stringResource(R.string.enter))
                }
            }

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {}
            ) {
                Text(stringResource(R.string.forgot_password))
            }
        }
    }
}
