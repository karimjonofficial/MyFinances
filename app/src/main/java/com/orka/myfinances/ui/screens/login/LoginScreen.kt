package com.orka.myfinances.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
    interactor: LoginScreenInteractor
) {
    val isErrorState   = state is LoginScreenState.Error
    val isLoadingState = state is LoginScreenState.Loading
    val textFieldError = isErrorState && !state.serverError

    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .scaffoldPadding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            AnimatedVisibility(
                visible = isErrorState,
                modifier = Modifier.align(Alignment.TopCenter),
                enter = fadeIn() + slideInVertically(),
                exit  = fadeOut() + slideOutVertically()
            ) {
                if (state is LoginScreenState.Error) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .background(
                                color = MaterialTheme.colorScheme.errorContainer,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.error),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )

                        HorizontalSpacer(12)
                        Text(
                            text = state.message.str(),
                            maxLines = 2,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // ── Main content ─────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val username       = rememberSaveable { mutableStateOf("") }
                val password       = rememberSaveable { mutableStateOf("") }
                val remember       = rememberSaveable { mutableStateOf(false) }
                val passwordVisible = rememberSaveable { mutableStateOf(false) }

                val usernameText = username.value
                val passwordText = password.value

                VerticalSpacer(48)

                // App logo + branding
                Image(
                    modifier = Modifier.size(88.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = stringResource(R.string.logo)
                )

                VerticalSpacer(24)
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                VerticalSpacer(4)
                Text(
                    text = stringResource(R.string.enter_to_account),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                VerticalSpacer(40)

                // ── Form card ───────────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoadingState,
                            isError = textFieldError,
                            singleLine = true,
                            value = usernameText,
                            shape = MaterialTheme.shapes.small,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.person),
                                    contentDescription = null
                                )
                            },
                            onValueChange = { username.value = it },
                            label = { Text(stringResource(R.string.username)) },
                            supportingText = {
                                if (textFieldError) {
                                    Text(stringResource(R.string.change_the_username))
                                }
                            }
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoadingState,
                            isError = textFieldError,
                            singleLine = true,
                            value = passwordText,
                            shape = MaterialTheme.shapes.small,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.lock),
                                    contentDescription = null
                                )
                            },
                            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                                    Icon(
                                        painter = painterResource(
                                            if (passwordVisible.value) R.drawable.visibility
                                            else R.drawable.visibility_off
                                        ),
                                        contentDescription = null
                                    )
                                }
                            },
                            onValueChange = { password.value = it },
                            label = { Text(stringResource(R.string.password)) },
                            supportingText = {
                                if (textFieldError) {
                                    Text(stringResource(R.string.change_the_password))
                                }
                            }
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                enabled = !isLoadingState,
                                checked = remember.value,
                                onCheckedChange = { remember.value = it }
                            )
                            Text(
                                text = stringResource(R.string.remember_me),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(Modifier.weight(1f))

                            TextButton(onClick = { /* TODO: forgot password */ }) {
                                Text(
                                    text = stringResource(R.string.forgot_password),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                VerticalSpacer(24)

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        if (usernameText.isNotEmpty() && passwordText.isNotEmpty()) {
                            if (remember.value) interactor.authorizeAndRemember(usernameText, passwordText)
                            else interactor.authorize(usernameText, passwordText)
                        }
                    },
                    enabled = (usernameText.isNotBlank() && passwordText.isNotBlank()) && !isLoadingState
                ) {
                    if (isLoadingState) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.enter),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                VerticalSpacer(64)
            }
        }
    }
}