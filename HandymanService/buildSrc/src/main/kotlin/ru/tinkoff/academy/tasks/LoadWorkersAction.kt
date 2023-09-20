package ru.tinkoff.academy.tasks

import net.datafaker.Faker
import java.net.URI

abstract class LoadWorkersAction : HttpWorkAction<LoadWorkersWork>() {

    override fun execute() {
        val request = workerGenerator.generate()
        executeHttp(request, URI.create(parameters.getWorkers().get()))
    }

    companion object {
        private val faker = Faker()
        private val workerGenerator = WorkerGenerator(faker)
    }
}