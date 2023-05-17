package ru.tinkoff.academy.tasks

import net.datafaker.Faker

class BankAccountGenerator(private val faker: Faker) {
    fun generate() =
        BankAccountRequest(
            cardId = faker.business().creditCardNumber(),
            paymentSystem = PaymentSystem.values()[faker.random().nextInt(PaymentSystem.values().size)],
            bank = banks[faker.random().nextInt(banks.size)]
        )

    companion object {
        val banks = arrayOf(
            "Cici Bank", "Bank of Emerica",
            "Kremniy Alley Bank", "Chicha construction Bank",
            "Bank of Prikolov", "Royal Bank of USCR",
            "Sperbank", "Credit Chicha Bank",
            "Bistro credit", "Slavebank"
        )
    }
}