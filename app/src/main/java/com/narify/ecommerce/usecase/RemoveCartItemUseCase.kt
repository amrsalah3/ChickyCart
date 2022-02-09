package com.narify.ecommerce.usecase

import com.narify.ecommerce.data.repository.CartRepository
import com.narify.ecommerce.model.Product
import javax.inject.Inject

class RemoveCartItemUseCase @Inject constructor(private val cartRepo: CartRepository) {
    suspend operator fun invoke(product: Product) = cartRepo.removeItem(product)
}