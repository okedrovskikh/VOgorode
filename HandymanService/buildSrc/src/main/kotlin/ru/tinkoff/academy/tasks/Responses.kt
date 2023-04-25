package ru.tinkoff.academy.tasks

data class BankAccountResponse(
    val id: Long,
    val cardId: String,
    val paymentSystem: PaymentSystem,
    val bank: String,
    val user: Any?
)
