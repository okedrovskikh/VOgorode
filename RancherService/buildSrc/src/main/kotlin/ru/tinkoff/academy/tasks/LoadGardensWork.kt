package ru.tinkoff.academy.tasks

import org.gradle.api.provider.Property
import org.gradle.workers.WorkParameters

interface LoadGardensWork : WorkParameters {
    fun getGardens() : Property<String>
}