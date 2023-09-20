package ru.tinkoff.academy.tasks

import net.datafaker.Faker
import java.util.UUID

class GardenGenerator(private val faker: Faker) {
    fun generate(): GardenRequest =
        GardenRequest(
            ownerId = UUID.randomUUID(),
            x1 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1),
            y1 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1),
            x2 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1),
            y2 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1),
            works = faker.random().let {
                val size = it.nextInt(1, 5)
                val res = mutableSetOf<WorkEnum>()
                for (i in 1..size) {
                    res.add(WorkEnum.values()[it.nextInt(4)])
                }
                res.toList()
            }
        )
}