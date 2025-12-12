package org.example.project.data.api.cart_api

import org.example.project.data.api.cart_api.model.GetCartResponseBody
import org.example.project.data.api.cart_api.model.UpdateCartAddressRequestBody
import org.example.project.data.api.cart_api.model.UpdateQuantityRequestBody

interface CartApi {
    suspend fun updateQuantity(
        updateQuantityRequestBody: UpdateQuantityRequestBody
    ): GetCartResponseBody

    suspend fun removeAll(): GetCartResponseBody

    suspend fun getCart(): GetCartResponseBody

    suspend fun updateAddress(body: UpdateCartAddressRequestBody): GetCartResponseBody

}