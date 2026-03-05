package com.orka.myfinances.ui.screens.home.viewmodel.profile

import com.orka.myfinances.data.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class InfoRepository(private val httpClient: HttpClient) : GetSingle<User> {
    override suspend fun getSingle(): User? {
        val response = httpClient.get(urlString = "users/me/")
        if(response.status == HttpStatusCode.OK) {
            val user = response.body<UserModel>().map()
            return user
        } else {
            return null
        }
    }
}