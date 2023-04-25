package ru.tinkoff.academy.tasks

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.datafaker.Faker
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import org.gradle.workers.WorkerExecutor
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject

abstract class LoadRancherDevDataTask : DefaultTask() {
    @Inject
    abstract fun getWorkerExecutor(): WorkerExecutor

    @get:Input
    var fields: URI = URI.create("http://localhost:8081/fields")

    @get:Input
    var fielders: URI = URI.create("http://localhost:8081/fielders")

    @get:InputFile
    var sqlFile = Path.of(System.getProperty("user.dir"), "buildSrc", "users_data.sql")

    @TaskAction
    fun run() {
        val workQueue = getWorkerExecutor().noIsolation()

        Files.newBufferedReader(sqlFile).use {
            var line = it.readLine()
            while (line != null) {
                workQueue.submit(LoadRancherDevDataWorkAction::class.java) {
                    it.getLine().set(line)
                    it.getFielders().set(fielders)
                    it.getFields().set(fields)
                }
            }
        }
    }
}
