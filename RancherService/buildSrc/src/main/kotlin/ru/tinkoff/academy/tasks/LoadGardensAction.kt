package ru.tinkoff.academy.tasks

import net.datafaker.Faker
import java.net.URI

abstract class LoadGardensAction : HttpWorkAction<LoadGardensWork>() {

    override fun execute() {
        val request = gardenGenerator.generate()
        executeHttp(request, URI.create(parameters.getGardens().get()))
    }

    companion object {
        private val faker = Faker()
        private val gardenGenerator = GardenGenerator(faker)
    }
}