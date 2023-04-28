package ru.tinkoff.academy.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.datafaker.Faker
import org.gradle.workers.WorkAction
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class LoadHandymanDevDataAction : WorkAction<LoadHandymanDevDataWork> {

    override fun execute() {
        val accountsId = (1..faker.random().nextInt(5))
            .map { executeHttp(bankAccountGenerator.generate(), accounts) }
            .map { mapper.readFromHttpBody<BankAccountResponse>(it) }
            .map { it.id }
        val pair = getEmailAndTelephone(parameters.getLine().get())
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
        private val faker = Faker()
        private val bankAccountGenerator = BankAccountGenerator(faker)
        private val userGenerator = UserGenerator(faker)
        private val client = HttpClient.newBuilder().build()
        private val mapper = jacksonObjectMapper()
        private lateinit var accounts: URI
        private lateinit var users: URI
        val regex = Regex("\\('.*', '.*', '(.*)', '(.*)', '.*', '.*'\\)")

        @JvmStatic
        fun setDefaultPhoto(photo: ByteArray) {
            userGenerator.defaultPhoto = photo
        }

        @JvmStatic
        fun setAccountsURI(accounts: URI) {
            this.accounts = accounts
        }

        @JvmStatic
        fun setUsersURI(users: URI) {
            this.users = users
        }
    }
}
