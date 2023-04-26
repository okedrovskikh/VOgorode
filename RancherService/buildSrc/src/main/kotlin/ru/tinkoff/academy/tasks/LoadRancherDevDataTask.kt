package ru.tinkoff.academy.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkerExecutor
import java.net.URI
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
    abstract var sqlFile: Path

    @TaskAction
    fun run() {
        val workQueue = getWorkerExecutor().noIsolation()

        Files.newBufferedReader(sqlFile).use {
            it.lines().filter { it.contains("rancher") }
                .forEach { line ->
                    workQueue.submit(LoadRancherDevDataAction::class.java) {
                        it.getLine().set(line)
                        it.getFielders().set(fielders)
                        it.getFields().set(fields)
                    }
                }
        }
    }
}
