package ru.tinkoff.academy.tasks

import net.datafaker.Faker

class BankAccountGenerator(private val faker: Faker) {
    fun generate() =
        BankAccountRequest(
            cardId = faker.business().creditCardNumber(),
            paymentSystem = PaymentSystem.values()[faker.random().nextInt(PaymentSystem.values().size)],
            bank = banks[faker.random().nextInt(banks.size)]
        )

    private fun generatePolygon():String {
        val x1 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        val x2 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        val y1 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        val y2 = (faker.random().nextDouble() + 0.1) * (faker.random().nextInt() + 0.1)
        return "POLYGON (($x1 $y1, $x1 $y2, $x2 $y2, $x2 $y2, $x1 $y1))"
    }

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