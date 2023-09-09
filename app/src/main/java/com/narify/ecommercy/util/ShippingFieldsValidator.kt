package com.narify.ecommercy.util

import android.util.Patterns

object ShippingFieldsValidator {
    fun isValidName(name: String): Boolean = name.isNotBlank()
    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidMobileNumber(number: String): Boolean = Patterns.PHONE.matcher(number).matches()
    fun isValidCountry(country: String): Boolean = country.isNotBlank()
    fun isValidState(state: String): Boolean = state.isNotBlank()
    fun isValidCity(city: String): Boolean = city.isNotBlank()
    fun isValidAddress(address: String): Boolean = address.isNotBlank()
}
