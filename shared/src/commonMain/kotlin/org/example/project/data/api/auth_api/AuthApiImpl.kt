package org.example.project.data.api.auth_api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.api.auth_api.model.CreateCartRequestBody
import org.example.project.data.api.auth_api.model.CreateCartResponse
import org.example.project.data.api.auth_api.model.GetAuthTypesResponseBody
import org.example.project.data.api.auth_api.model.RefreshTokenRequestBody
import org.example.project.data.api.auth_api.model.RefreshTokenResponseBody
import org.example.project.data.api.auth_api.model.SendSmsRequestBody
import org.example.project.data.api.auth_api.model.SendSmsResponseBody
import org.example.project.data.api.auth_api.model.VerificationRequestBody
import org.example.project.data.api.auth_api.model.VerificationResponseBody
import org.example.project.data.entities.AddressEntity
import org.example.project.domain.models.DeliveryType

class AuthApiImpl(private val httpClient: HttpClient) : AuthApi {
    override suspend fun getAuthTypes(): GetAuthTypesResponseBody {
        return httpClient.get("auth/authTypes").body()
    }

    override suspend fun sendVerificationCode(sendSmsRequestBody: SendSmsRequestBody): SendSmsResponseBody {
        return httpClient.post("auth/sendSms") {
            contentType(ContentType.Application.Json)
            setBody(sendSmsRequestBody)
        }.body()
    }

    override suspend fun verify(verificationRequest: VerificationRequestBody): VerificationResponseBody {
        return httpClient.post("auth/verify") {
            contentType(ContentType.Application.Json)
            setBody(verificationRequest)
        }.body()
    }

    override suspend fun refreshTokens(refreshTokenRequestBody: RefreshTokenRequestBody): RefreshTokenResponseBody {
        return httpClient.post("auth/refreshTokens") {
            contentType(ContentType.Application.Json)
            setBody(refreshTokenRequestBody)
        }.body()
    }

    override suspend fun createCart(body: CreateCartRequestBody): CreateCartResponse {
        return httpClient.post("auth/createCart") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }
}