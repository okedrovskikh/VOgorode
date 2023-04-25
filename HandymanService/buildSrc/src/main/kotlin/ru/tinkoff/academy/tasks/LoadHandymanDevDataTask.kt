package ru.tinkoff.academy.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path

abstract class LoadHandymanDevDataTask : DefaultTask() {
    private val faker = Faker()
    private val bankAccountGenerator = BankAccountGenerator(faker)
    private val userGenerator = UserGenerator(faker)
    private val client = HttpClient.newBuilder().build()
    private val mapper = jacksonObjectMapper()

    @get:Input
    var skipLines = 0

    @get:Input
    var accounts: URI = URI.create("http://localhost:8081/accounts")

    @get:Input
    var users: URI = URI.create("http://localhost:8081/users")

    @get:InputFile
    var sqlFile = Path.of(System.getProperty("user.dir"), "buildSrc", "users_data.sql")

    @get:InputFile
    var photoFile = Path(System.getProperty("user.dir"), "buildSrc", "default-photo.jpg")

    @get:Input
    var threadPoolSize = Runtime.getRuntime().availableProcessors() / 2

    @OptIn(DelicateCoroutinesApi::class)
    @TaskAction
    fun run() {
        userGenerator.defaultPhoto = photoFile.toFile().readBytes()
        val thPool = newFixedThreadPoolContext(threadPoolSize, "Executor")
        runBlocking(thPool) {
            Files.newBufferedReader(sqlFile).use {
                it.readLines().filter { it.contains("handyman") }
                    .drop(skipLines)
                    .forEach { launch { execute(it) } }
            }
        }
    }

    private suspend fun execute(line: String) {
        val accountsId = (1..faker.random().nextInt(5))
            .map { executeHttp(bankAccountGenerator.generate(), accounts) }
            .map { mapper.readFromHttpBody<BankAccountResponse>(it) }
            .map { it.id }
        val pair = getEmailAndTelephone(line)
        executeHttp(userGenerator.generate(pair.first, pair.second, accountsId), users)
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