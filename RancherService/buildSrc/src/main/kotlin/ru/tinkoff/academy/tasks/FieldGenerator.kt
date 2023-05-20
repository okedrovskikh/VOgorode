package ru.tinkoff.academy.tasks

import net.datafaker.Faker

class FieldGenerator(private val faker: Faker) {
    fun generate() =
        FieldRequest(
            address = faker.address().fullAddress(),
            latitude = faker.address().latitude().toDouble(),
            longitude = faker.address().longitude().toDouble(),
            area = generatePolygon()
        )

    private fun generatePolygon(): String {
        val x1 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        val x2 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        val y1 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        val y2 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        return "POLYGON (($x1 $y1, $x1 $y2, $x2 $y2, $x2 $y1, $x1 $y1))"
    }
}