package ru.tinkoff.academy.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkerExecutor
import java.net.URI
import javax.inject.Inject

abstract class LoadGardensTask @Inject constructor(private val workerExecutor: WorkerExecutor) : DefaultTask() {

    @get:Input
    var gardens: URI = URI.create("http://localhost:8081/gardens")

    @TaskAction
    fun run() {
        val workQueue = workerExecutor.noIsolation()

        for (i in 0 until 50000) {
            workQueue.submit(LoadGardensAction::class.java) {
                it.getGardens().set(gardens.toString())
            }
        }

        workQueue.await()
    }
}