package ru.tinkoff.academy.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.gradle.api.DefaultTask
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.lang.System.Logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicLong
import kotlin.coroutines.CoroutineContext

abstract class LoadRancherDevDataTask() : DefaultTask() {
    private val faker = Faker()
    private val fieldGenerator = FieldGenerator(faker)
    private val fielderGenerator = FielderGenerator(faker)
    private val client: HttpClient = HttpClient.newBuilder().build()
    private val mapper = jacksonObjectMapper()

    @get:Input
    var skipLines: Int = 0

    @get:Input
    var fields: URI = URI.create("http://localhost:8081/fields")

    @get:Input
    var fielders: URI = URI.create("http://localhost:8081/fielders")

    @get:InputFile
    var sqlFile = Path.of(System.getProperty("user.dir"), "buildSrc", "users_data.sql")

    @get:Input
    var threadPoolSize = Runtime.getRuntime().availableProcessors() / 2

    @OptIn(DelicateCoroutinesApi::class)
    @TaskAction
    fun run() {
        val thPool = newFixedThreadPoolContext(threadPoolSize, "Executor")
        runBlocking {
            Files.newBufferedReader(sqlFile).use {
                it.readLines().filter { it.contains("rancher") }
                    .drop(skipLines)
                    .forEach { launch(thPool) { execute(it) } }
            }
        }
    }

    private suspend fun execute(line: String) {
        val fieldsId = (1..faker.random().nextInt(1, 5))
            .map { executeHttp(fieldGenerator.generate(), fields) }
            .map { mapper.readFromHttpBody<FieldResponse>(it) }
            .map { it.id }
        val pair = getEmailAndTelephone(line)
        executeHttp(fielderGenerator.generate(pair.first, pair.second, fieldsId), fielders)
    }

    private fun <T> executeHttp(obj: T, uri: URI): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(obj)))
            .header("Content-Type", "application/json")
            .build()

        return client.send(request, HttpResponse.BodyHandlers.ofString()).also {
            if (it.statusCode() != 200) {
                throw IllegalStateException(it.body())
            }
        }
    }

    private inline fun <reified R> com.fasterxml.jackson.databind.ObjectMapper.readFromHttpBody(httpResponse: HttpResponse<String>): R =
        this.readValue(httpResponse.body())

    private fun getEmailAndTelephone(line: String): Pair<String, String> {
        return regex.find(line)!!.groups.let { Pair(it[1]!!.value, it[2]!!.value) }
    }

    companion object {
        val regex = Regex("\\('.*', '.*', '(.*)', '(.*)', '.*', '.*'\\)")
    }
}