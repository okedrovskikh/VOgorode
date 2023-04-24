package ru.tinkoff.academy.tasks

data class FieldRequest(
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val area: String
)

data class FielderRequest(
    val name: String,
    val surname: String,
    val email: String,
    val telephone: String,
    val fieldsId: List<Long>
)
