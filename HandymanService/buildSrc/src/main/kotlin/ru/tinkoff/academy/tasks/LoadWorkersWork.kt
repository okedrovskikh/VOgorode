package ru.tinkoff.academy.tasks

import org.gradle.api.provider.Property
import org.gradle.workers.WorkParameters

interface LoadWorkersWork : WorkParameters {
    fun getWorkers(): Property<String>
}