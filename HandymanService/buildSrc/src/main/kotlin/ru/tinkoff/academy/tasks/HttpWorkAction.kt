package ru.tinkoff.academy.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class HttpWorkAction<T : WorkParameters> : WorkAction<T> {
    protected val client = HttpClient.newBuilder().build()
    protected val mapper = jacksonObjectMapper()

    protected fun <T> executeHttp(obj: T, uri: URI): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(obj)))
            .header("Content-Type", "application/json")
            .build()

        return client.send(request, HttpResponse.BodyHandlers.ofString()).also {
            if (it.statusCode() != 200) {
                println(it.body())
                error(it.body())
            }
        }
    }
}