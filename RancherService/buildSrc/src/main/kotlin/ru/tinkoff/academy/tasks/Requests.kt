package ru.tinkoff.academy.tasks

import java.util.UUID

data class FieldRequest(
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val area: List<Double>
)

data class GardenerRequest(
    val name: String,
    val surname: String,
    val email: String,
    val telephone: String,
    val fieldsId: List<Long>
)

data class GardenRequest(
    val ownerId: UUID,
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double,
    val works: List<WorkEnum>
)

enum class WorkEnum {
    plant, water, sow, shovel
}
