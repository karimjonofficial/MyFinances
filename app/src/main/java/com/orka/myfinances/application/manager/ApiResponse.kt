package com.orka.myfinances.application.manager

interface ApiResponse {
    data object Unauthorized : ApiResponse
    data object Failure : ApiResponse
    data class Success(val data: Any) : ApiResponse
}