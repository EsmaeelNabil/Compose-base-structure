package com.esmaeel.composestructure.data

data class ErrorModel(
    val error: Error,
    val status: Int,
    val success: Boolean
) {
    data class Error(
        val code: String,
        val message: String?,
        val fields: Fields?,
    ) {
        data class Fields(
            val type: String
        )
    }
}