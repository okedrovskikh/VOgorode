package ru.tinkoff.academy.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.datafaker.Faker
import org.gradle.workers.WorkAction
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class LoadRancherDevDataAction() : WorkAction<LoadRancherDevDataWork> {
    private val client = HttpClient.newHttpClient()

    override fun execute() {
        val fieldsId = (1..faker.random().nextInt(1, 5))
            .map { executeHttp(fieldGenerator.generate(), fielders) }
            .map { mapper.readFromHttpBody<FieldResponse>(it) }
            .map { it.id }
        val pair = getEmailAndTelephone(parameters.getLine().get())
        executeHttp(fielderGenerator.generate(pair.first, pair.second, fieldsId), fields)
    }

    private fun <T> executeHttp(obj: T, uri: URI): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(obj)))
            .header("Content-Type", "application/json")
            .build()

        return client.send(request, HttpResponse.BodyHandlers.ofString()).also {
            if (it.statusCode() != 200) {
                error(it.body())
            }
        }
    }

    private inline fun <reified R> com.fasterxml.jackson.databind.ObjectMapper.readFromHttpBody(httpResponse: HttpResponse<String>): R =
        this.readValue(httpResponse.body())

    private fun getEmailAndTelephone(line: String): Pair<String, String> {
        return regex.find(line)!!.groups.let { Pair(it[1]!!.value, it[2]!!.value) }
    }

    companion object {
        private val faker = Faker()
        private val fieldGenerator = FieldGenerator(faker)
        private val fielderGenerator = FielderGenerator(faker)
        private val mapper = jacksonObjectMapper()
        private lateinit var fielders: URI
        private lateinit var fields: URI
        val regex = Regex("\\('.*', '.*', '(.*)', '(.*)', '.*', '.*'\\)")

        @JvmStatic
        fun setFieldersURI(fielders: URI) {
            this.fielders = fielders
        }

        @JvmStatic
        fun setFieldsURI(fields: URI) {
            this.fields = fields
        }
    }
}
