package ru.tinkoff.academy.tasks

import com.fasterxml.jackson.module.kotlin.readValue
import net.datafaker.Faker
import java.net.URI
import java.net.http.HttpResponse

abstract class LoadRancherDevDataAction() : HttpWorkAction<LoadRancherDevDataWork>() {

    override fun execute() {
        val fieldsId = (1..faker.random().nextInt(1, 5))
            .map { executeHttp(fieldGenerator.generate(), URI.create(parameters.getFields().get())) }
            .map { mapper.readFromHttpBody<FieldResponse>(it) }
            .map { it.id }
        val pair = getEmailAndTelephone(parameters.getLine().get())
        executeHttp(gardenerGenerator.generate(pair.first, pair.second, fieldsId), URI.create(parameters.getFielers().get()))
    }

    private inline fun <reified R> com.fasterxml.jackson.databind.ObjectMapper.readFromHttpBody(httpResponse: HttpResponse<String>): R =
        this.readValue(httpResponse.body())

    private fun getEmailAndTelephone(line: String): Pair<String, String> {
        return regex.find(line)!!.groups.let { Pair(it[1]!!.value, it[2]!!.value) }
    }

    companion object {
        private val faker = Faker()
        private val fieldGenerator = FieldGenerator(faker)
        private val gardenerGenerator = GardenerGenerator(faker)
        val regex = Regex("\\('.*', '.*', '(.*)', '(.*)', '.*', '.*'\\)")
    }
}
