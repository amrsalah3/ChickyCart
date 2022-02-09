package com.narify.ecommerce.model

data class User(
    val name: Name? = Name(),
    val email: String? = "no email",
    val photoUrl: String? = "no photo",
    val age: Int = -1,
    val gender: Gender = Gender.UNDEFINED,
    val phoneNumber: String? = "no phone number",
    val address: Address? = Address(),
    val card: Card? = Card(),
    val transaction: Transaction? = Transaction()
) {
    data class Name(
        val first: String? = "no name",
        val middle: String? = "",
        val last: String? = ""
    )

    enum class Gender {
        MALE, FEMALE, UNDEFINED
    }

    data class Address(val name: String? = "no address")

    data class Card(val name: String? = "no card", val number: String? = "000000000")

    data class Transaction(val id: String? = "no transaction")

}
