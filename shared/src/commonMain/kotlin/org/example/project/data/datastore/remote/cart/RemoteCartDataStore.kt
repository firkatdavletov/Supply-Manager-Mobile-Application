package org.example.project.data.datastore.remote.cart

import org.example.project.data.api.auth_api.model.CreateCartRequestBody
import org.example.project.data.api.auth_api.model.CreateCartResponse
import org.example.project.data.api.cart_api.model.GetCartResponseBody
import org.example.project.data.api.cart_api.model.UpdateCartAddressRequestBody

interface RemoteCartDataStore {
    suspend fun getCart(): GetCartResponseBody
    suspend fun createCart(body: CreateCartRequestBody): CreateCartResponse
    suspend fun updateQuantity(productId: Long, quantity: Int): GetCartResponseBody
    suspend fun removeAll(): GetCartResponseBody
    suspend fun updateCartAddress(body: UpdateCartAddressRequestBody): GetCartResponseBody
}