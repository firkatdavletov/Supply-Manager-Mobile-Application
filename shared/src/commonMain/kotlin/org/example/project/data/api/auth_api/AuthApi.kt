package org.example.project.data.api.auth_api

import org.example.project.data.api.auth_api.model.CreateCartRequestBody
import org.example.project.data.api.auth_api.model.CreateCartResponse
import org.example.project.data.api.auth_api.model.GetAuthTypesResponseBody
import org.example.project.data.api.auth_api.model.RefreshTokenRequestBody
import org.example.project.data.api.auth_api.model.RefreshTokenResponseBody
import org.example.project.data.api.auth_api.model.SendSmsRequestBody
import org.example.project.data.api.auth_api.model.SendSmsResponseBody
import org.example.project.data.api.auth_api.model.VerificationRequestBody
import org.example.project.data.api.auth_api.model.VerificationResponseBody

interface AuthApi {
    suspend fun getAuthTypes(): GetAuthTypesResponseBody

    suspend fun sendVerificationCode(sendSmsRequestBody: SendSmsRequestBody): SendSmsResponseBody

    suspend fun verify(verificationRequest: VerificationRequestBody): VerificationResponseBody

    suspend fun refreshTokens(refreshTokenRequestBody: RefreshTokenRequestBody): RefreshTokenResponseBody

    suspend fun createCart(body: CreateCartRequestBody): CreateCartResponse
}