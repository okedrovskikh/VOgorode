package ru.tinkoff.academy.tasks

data class FieldResponse(
    val id: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val fielder: Any?,
    val area: String
)
