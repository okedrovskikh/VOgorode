package ru.tinkoff.academy.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkerExecutor
import java.net.URI
import javax.inject.Inject

abstract class LoadWorkersTask @Inject constructor(private val workerExecutor: WorkerExecutor) : DefaultTask() {

    @get:Input
    var workers: URI = URI.create("http://localhost:8080/workers")

    @TaskAction
    fun run() {
        val workQueue = workerExecutor.noIsolation()

        for (i in 0 until 50000) {
            workQueue.submit(LoadWorkersAction::class.java) {
                it.getWorkers().set(workers.toString())
            }
        }

        workQueue.await()
    }
}