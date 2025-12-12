package org.example.project.data.datastore.remote.auth

import kotlinx.coroutines.flow.Flow
import org.example.project.data.api.auth_api.model.GetAuthTypesResponseBody
import org.example.project.data.api.auth_api.model.SendSmsRequestBody
import org.example.project.data.api.auth_api.model.SendSmsResponseBody
import org.example.project.data.api.auth_api.model.VerificationResponseBody
import org.example.project.data.entities.VerifyPhoneResponseModel

interface AuthRemoteDataStore {
    suspend fun getAuthTypes(): GetAuthTypesResponseBody
    suspend fun sendVerificationCode(request: SendSmsRequestBody): SendSmsResponseBody
    suspend fun verify(phone: String, code: String): VerificationResponseBody
}