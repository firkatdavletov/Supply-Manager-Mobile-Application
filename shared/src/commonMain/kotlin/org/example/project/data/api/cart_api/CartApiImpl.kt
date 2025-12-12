package org.example.project.data.api.cart_api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import org.example.project.data.api.cart_api.model.GetCartResponseBody
import org.example.project.data.api.cart_api.model.UpdateCartAddressRequestBody
import org.example.project.data.api.cart_api.model.UpdateQuantityRequestBody

class CartApiImpl(private val httpClient: HttpClient) : CartApi {
    override suspend fun updateQuantity(updateQuantityRequestBody: UpdateQuantityRequestBody): GetCartResponseBody {
        val response = httpClient.put("cart/updateQuantity") {
            contentType(ContentType.Application.Json)
            setBody(updateQuantityRequestBody)
        }
        return response.body<GetCartResponseBody>()
    }

    override suspend fun removeAll(): GetCartResponseBody {
        val response = httpClient.delete("cart/removeAll")
        return response.body<GetCartResponseBody>()
    }

    override suspend fun getCart(): GetCartResponseBody{
        val response = httpClient.get {
            url {
                path("cart")
            }
        }
        return response.body()
    }

    override suspend fun updateAddress(body: UpdateCartAddressRequestBody): GetCartResponseBody {
        val response = httpClient.post {
            url {
                path("cart/updateAddress")
            }
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return response.body<GetCartResponseBody>()
    }
}