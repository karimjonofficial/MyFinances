package com.orka.myfinances.lib.viewmodel.mappers

import com.orka.myfinances.lib.ui.state.FailureType
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.failure.CoroutineCancellationFailure
import com.orka.myfinances.lib.viewmodel.failure.NetworkFailure
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

class NetworkExceptionMapper<T> : ExceptionMapper<T> {
    override suspend fun map(
        oldState: State<T>?,
        e: Exception
    ): State<T> {
        val failure = when (e) {
            is CancellationException -> CoroutineCancellationFailure
            is UnresolvedAddressException -> NetworkFailure.NoInternet
            is ConnectTimeoutException -> NetworkFailure.ConnectTimeout
            is SocketTimeoutException -> NetworkFailure.SocketTimeout
            is HttpRequestTimeoutException -> NetworkFailure.RequestTimeout
            is ClientRequestException -> NetworkFailure.Http(e.response.status.value)
            is ServerResponseException -> NetworkFailure.Http(e.response.status.value)
            is RedirectResponseException -> NetworkFailure.Http(e.response.status.value)
            else -> FailureType.Exception(e.message.orEmpty())
        }

        return State.Failure(
            type = failure,
            value = oldState?.value
        )
    }
}