package com.orka.myfinances.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    interactor: LoginScreenInteractor
) {

    Scaffold(modifier = modifier) { paddingValues ->
        Box(modifier = Modifier.scaffoldPadding(paddingValues)) {
            if(state is LoginScreenState.Error) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.error),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )

                    HorizontalSpacer(16)
                    Text(
                        text = state.message.str(),
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val username = rememberSaveable { mutableStateOf("admin") }
                val password = rememberSaveable { mutableStateOf("123") }
                val remember = rememberSaveable { mutableStateOf(false) }
                val passwordVisible = rememberSaveable { mutableStateOf(false) }

                val usernameText = username.value
                val passwordText = password.value

                val isErrorState = state is LoginScreenState.Error
                val isLoadingState = state is LoginScreenState.Loading
                val textFieldError = isErrorState && !state.serverError

                VerticalSpacer(32)
                Image(
                    modifier = Modifier.size(128.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = stringResource(R.string.logo)
                )

                VerticalSpacer(16)
                Text(
                    text = stringResource(R.string.enter_to_account),
                    style = MaterialTheme.typography.headlineMedium
                )

                VerticalSpacer(16)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoadingState,
                    isError = textFieldError,
                    singleLine = true,
                    value = username.value,
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
                    value = password.value,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.lock),
                            contentDescription = null
                        )
                    },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible.value = !passwordVisible.value }
                        ) {
                            Icon(
                                painter = painterResource(if (passwordVisible.value) R.drawable.visibility else R.drawable.visibility_off),
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

                VerticalSpacer(8)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        enabled = !isLoadingState,
                        checked = remember.value,
                        onCheckedChange = { remember.value = it }
                    )
                    HorizontalSpacer(8)
                    Text(stringResource(R.string.remember_me))
                }

                VerticalSpacer(8)
                Button(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    onClick = {
                        val username = username.value
                        val password = password.value
                        if (username.isNotEmpty() && password.isNotEmpty()) {
                            if (remember.value) interactor.authorizeAndRemember(username, password)
                            else interactor.authorize(username, password)
                        }
                    },
                    enabled = (usernameText.isNotBlank() && passwordText.isNotBlank()) && !isLoadingState
                ) {
                    if (isLoadingState)
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    else Text(stringResource(R.string.enter))
                }

                VerticalSpacer(8)
                Text(stringResource(R.string.forgot_password))
                VerticalSpacer(64)
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    MyFinancesTheme {
        LoginScreen(
            state = LoginScreenState.Initial,
            interactor = LoginScreenInteractor.dummy
        )
    }
}