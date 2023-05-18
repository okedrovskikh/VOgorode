package ru.tinkoff.academy.tasks

import net.datafaker.Faker

class UserGenerator(private val faker: Faker) {
    fun generate(email: String, telephone: String, accountsId: List<Long>): UserRequest =
        UserRequest(
            name = faker.name().firstName(),
            surname = faker.name().lastName(),
            skills = faker.random().let {
                val size = it.nextInt(1, 5)
                val res = mutableSetOf<WorkEnum>()
                for (i in 1..size) {
                    res.add(WorkEnum.values()[it.nextInt(4)])
                }
                res.toTypedArray()
            },
            email = email,
            telephone = telephone,
            accountsId = accountsId,
            photo = arrayOf()
        )
}