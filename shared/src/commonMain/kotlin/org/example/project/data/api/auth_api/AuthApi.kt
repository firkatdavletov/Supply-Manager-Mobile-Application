package org.example.project.data.api.auth_api

import kotlinx.coroutines.flow.SharedFlow
import org.example.project.data.api.auth_api.model.CheckSmsCodeRequestBody
import org.example.project.data.api.auth_api.model.CheckSmsCodeResponseBody
import org.example.project.data.api.auth_api.model.CreateCartRequestBody
import org.example.project.data.api.auth_api.model.CreateCartResponse
import org.example.project.data.api.auth_api.model.GetAuthTypesResponseBody
import org.example.project.data.api.auth_api.model.LoginByEmailRequestBody
import org.example.project.data.api.auth_api.model.RefreshTokenRequestBody
import org.example.project.data.api.auth_api.model.RefreshTokenResponseBody
import org.example.project.data.api.auth_api.model.TokenPairDto
import org.example.project.data.api.auth_api.model.VerifyPhoneNumberRequestBody
import org.example.project.data.api.auth_api.model.VerifyPhoneNumberResponseBody
import org.example.project.data.entities.TokenPairEntity

interface AuthApi {
    val updates: SharedFlow<TokenPairEntity>

    suspend fun getAuthTypes(): GetAuthTypesResponseBody

    suspend fun verifyPhoneNumber(
        verifyPhoneNumberRequestBody: VerifyPhoneNumberRequestBody,
    ): VerifyPhoneNumberResponseBody

    suspend fun checkSmsCode(body: CheckSmsCodeRequestBody): CheckSmsCodeResponseBody

    suspend fun loginByEmail(body: LoginByEmailRequestBody): TokenPairDto

    suspend fun refreshTokens(refreshTokenRequestBody: RefreshTokenRequestBody): RefreshTokenResponseBody

    suspend fun createCart(body: CreateCartRequestBody): CreateCartResponse

    suspend fun connect(checkId: String)

    suspend fun disconnect()
}
