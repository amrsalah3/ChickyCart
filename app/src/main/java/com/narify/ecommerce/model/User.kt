package com.narify.ecommerce.model

data class User(
    val name: String? = "no name",
    val email: String? = "no email",
    val photoUrl: String? = "no photo",
    val age: Int = -1,
    val gender: Gender = Gender.UNDEFINED,
    val phoneNumber: String? = "no phone number"
) {
    enum class Gender {
        MALE, FEMALE, UNDEFINED
    }
}
