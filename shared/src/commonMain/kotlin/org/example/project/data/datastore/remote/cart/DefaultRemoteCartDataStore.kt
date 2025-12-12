package org.example.project.data.datastore.remote.cart

import org.example.project.data.api.auth_api.AuthApi
import org.example.project.data.api.cart_api.CartApi
import org.example.project.data.api.auth_api.model.CreateCartRequestBody
import org.example.project.data.api.auth_api.model.CreateCartResponse
import org.example.project.data.api.cart_api.model.GetCartResponseBody
import org.example.project.data.api.cart_api.model.UpdateCartAddressRequestBody
import org.example.project.data.api.cart_api.model.UpdateQuantityRequestBody

class DefaultRemoteCartDataStore(
    private val cartApi: CartApi,
    private val authApi: AuthApi,
): RemoteCartDataStore {
    override suspend fun getCart(): GetCartResponseBody {
        return cartApi.getCart()
    }

    override suspend fun createCart(body: CreateCartRequestBody): CreateCartResponse {
        return authApi.createCart(body)
    }

    override suspend fun updateQuantity(
        productId: Long,
        quantity: Int
    ): GetCartResponseBody {
        val request = UpdateQuantityRequestBody(productId, quantity)
        return cartApi.updateQuantity(request)
    }

    override suspend fun removeAll(): GetCartResponseBody{
        return cartApi.removeAll()
    }

    override suspend fun updateCartAddress(body: UpdateCartAddressRequestBody): GetCartResponseBody {
        return cartApi.updateAddress( body)
    }
}