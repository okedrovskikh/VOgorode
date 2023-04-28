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

abstract class LoadHandymanDevDataTask : DefaultTask() {
    @Inject
    abstract fun getWorkerExecutor(): WorkerExecutor

    @get:Input
    var users: URI = URI.create("http://localhost:8080/accounts")

    @get:Input
    var accounts: URI = URI.create("http://localhost:8080/users")

    @get:InputFile
    abstract var sqlFile: Path

    @get:InputFile
    var photoFile: Path = Path.of(System.getProperty("user.dir"), "buildSrc", "default-photo.jpg")

    @TaskAction
    fun run() {
        LoadHandymanDevDataAction.setDefaultPhoto(photoFile.toFile().readBytes())
        LoadHandymanDevDataAction.setAccountsURI(accounts)
        LoadHandymanDevDataAction.setUsersURI(users)

        var workQueue = getWorkerExecutor().processIsolation() { it.forkOptions.maxHeapSize = "2048m" }

        Files.newBufferedReader(sqlFile).use {
            it.lines().filter { it.contains("handyman") }
                .forEach { line ->
                    workQueue.submit(LoadHandymanDevDataAction::class.java) {
                        it.getLine().set(line)
                    }
                }
        }

        workQueue.await()
    }
}
