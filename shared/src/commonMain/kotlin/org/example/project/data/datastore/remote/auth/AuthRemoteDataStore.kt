package org.example.project.data.datastore.remote.auth

import kotlinx.coroutines.flow.SharedFlow
import org.example.project.data.api.auth_api.model.CheckSmsCodeResponseBody
import org.example.project.data.api.auth_api.model.GetAuthTypesResponseBody
import org.example.project.data.api.auth_api.model.VerifyPhoneNumberRequestBody
import org.example.project.data.api.auth_api.model.VerifyPhoneNumberResponseBody
import org.example.project.data.entities.TokenPairEntity

interface AuthRemoteDataStore {
    val updates: SharedFlow<TokenPairEntity>

    suspend fun getAuthTypes(): GetAuthTypesResponseBody

    suspend fun verifyPhoneNumber(request: VerifyPhoneNumberRequestBody): VerifyPhoneNumberResponseBody

    suspend fun checkSmsCode(
        phone: String,
        code: String,
    ): CheckSmsCodeResponseBody

    suspend fun connect(checkId: String)

    suspend fun disconnect()
}