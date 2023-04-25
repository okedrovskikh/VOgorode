package ru.tinkoff.academy.tasks

data class BankAccountResponse(
    val id: Long,
    val address: String,
    val latitude: String,
    val longitude: String,
    val fielder: Any?,
    val area: String
)
