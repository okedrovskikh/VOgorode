package ru.tinkoff.academy.tasks

import org.gradle.api.provider.Property
import org.gradle.workers.WorkParameters
import java.net.URI

interface LoadHandymanDevDataWork : WorkParameters {
    fun getLine(): Property<String>
}