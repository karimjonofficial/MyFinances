package com.orka.myfinances.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.User
import com.orka.myfinances.lib.data.models.Person
import com.orka.myfinances.ui.screens.clients.ClientCard

@Composable
fun PersonCard(
    modifier: Modifier = Modifier,
    person: Person,
    onClick: () -> Unit
) {
    when(person) {

        is Client -> {
            ClientCard(
                modifier = modifier,
                client = person,
                onClick = { onClick() }
            )
        }

        is User -> {
            UserCard(
                modifier = modifier,
                user = person,
                onClick = { onClick() }
            )
        }
    }
}