package ru.tinkoff.academy.tasks

import org.gradle.api.provider.Property
import org.gradle.workers.WorkParameters

interface LoadHandymanDevDataWork : WorkParameters {
    fun getLine(): Property<String>
    fun getAccounts(): Property<String>
    fun getUsers(): Property<String>
}