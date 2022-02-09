package com.narify.ecommerce.usecase

import com.narify.ecommerce.data.repository.CartRepository
import com.narify.ecommerce.model.CartItem
import com.narify.ecommerce.model.Result
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(private val cartRepo: CartRepository) {
    suspend operator fun invoke(): Result<List<CartItem>> = cartRepo.getItems()
}