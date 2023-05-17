package ru.tinkoff.academy.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import net.datafaker.Faker
import org.gradle.workers.WorkAction
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

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