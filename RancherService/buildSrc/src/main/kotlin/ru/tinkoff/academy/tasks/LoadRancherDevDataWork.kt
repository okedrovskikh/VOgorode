package ru.tinkoff.academy.tasks

import org.gradle.api.provider.Property
import org.gradle.workers.WorkParameters
import java.net.URI

interface LoadRancherDevDataWork : WorkParameters {
    fun getLine(): Property<String>
    fun getFields(): Property<String>
    fun getFielers(): Property<String>
}
