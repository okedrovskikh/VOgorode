package ru.tinkoff.academy.tasks

import net.datafaker.Faker

class FielderGenerator(private val faker: Faker) {
    fun generate(email: String, telephone: String, fieldsId: List<Long>): FielderRequest =
        FielderRequest(
            name = faker.name().firstName(),
            surname = faker.name().lastName(),
            email = email,
            telephone = telephone,
            fieldsId = fieldsId
        )
}