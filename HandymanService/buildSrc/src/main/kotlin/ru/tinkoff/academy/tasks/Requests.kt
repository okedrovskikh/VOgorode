package ru.tinkoff.academy.tasks

data class BankAccountRequest(
    val cardId: String,
    val paymentSystem: PaymentSystem,
    val bank: String
)

enum class PaymentSystem {
    mastercard, visa, mir, unionpay
}

data class UserRequest(
    val name: String,
    val surname: String,
    val skills: Array<WorkEnum>,
    val email: String,
    val telephone: String,
    val accountsId: List<Long>,
    val photo: Array<Byte>
)

enum class WorkEnum {
    plant, water, sow, shovel
}
