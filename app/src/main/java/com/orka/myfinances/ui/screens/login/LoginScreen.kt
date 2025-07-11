package com.orka.myfinances.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.HorizontalSpacer
import com.orka.myfinances.lib.VerticalSpacer

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginScreenState,
    viewModel: LoginScreenViewModel
) {
    val isErrorState = uiState is LoginScreenState.Error
    val isLoadingState = uiState is LoginScreenState.Loading

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        val remember = rememberSaveable { mutableStateOf(false) }
        val passwordVisible = rememberSaveable { mutableStateOf(false) }

        val usernameText = username.value
        val passwordText = password.value


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
            isError = isErrorState,
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
                if(isErrorState)
                    Text(stringResource(R.string.change_the_username))
            }
        )

        VerticalSpacer(8)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoadingState,
            isError = isErrorState,
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
                if(isErrorState)
                    Text(stringResource(R.string.change_the_password))
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
                if(username.isNotEmpty() && password.isNotEmpty()) {
                    if (remember.value) viewModel.authorizeAndRemember(username, password)
                    else viewModel.authorize(username, password)
                }
            },
            enabled = (usernameText.isNotBlank() && passwordText.isNotBlank()) && !isLoadingState
        ) {
            if(isLoadingState) CircularProgressIndicator(modifier = Modifier.size(24.dp))
            else Text(stringResource(R.string.enter))
        }

        VerticalSpacer(8)
        Text(stringResource(R.string.forgot_password))
        VerticalSpacer(64)
    }
}