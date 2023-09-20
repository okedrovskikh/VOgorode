package ru.tinkoff.academy.tasks

import net.datafaker.Faker

class WorkerGenerator(private val faker: Faker) {
    fun generate(): WorkerRequest =
        WorkerRequest(
            login = faker.name().username(),
            email = faker.internet().emailAddress(),
            telephone = faker.phoneNumber().phoneNumber(),
            services = faker.random().let {
                val size = it.nextInt(1, 5)
                val res = mutableSetOf<WorkEnum>()
                for (i in 1..size) {
                    res.add(WorkEnum.values()[it.nextInt(4)])
                }
                res.toList()
            },
            latitude = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1),
            longitude = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        )
}