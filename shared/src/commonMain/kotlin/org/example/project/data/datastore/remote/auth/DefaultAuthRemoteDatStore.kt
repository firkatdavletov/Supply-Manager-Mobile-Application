package org.example.project.data.datastore.remote.auth

import org.example.project.data.api.auth_api.AuthApi
import org.example.project.data.api.auth_api.model.GetAuthTypesResponseBody
import org.example.project.data.api.auth_api.model.SendSmsRequestBody
import org.example.project.data.api.auth_api.model.SendSmsResponseBody
import org.example.project.data.api.auth_api.model.VerificationRequestBody
import org.example.project.data.api.auth_api.model.VerificationResponseBody

class DefaultAuthRemoteDatStore(
    private val authApi: AuthApi,
): AuthRemoteDataStore {
    override suspend fun getAuthTypes(): GetAuthTypesResponseBody {
        return authApi.getAuthTypes()
    }

    override suspend fun sendVerificationCode(request: SendSmsRequestBody): SendSmsResponseBody {
        return authApi.sendVerificationCode(request)
    }

    override suspend fun verify(
        phone: String,
        code: String
    ): VerificationResponseBody {
        val request = VerificationRequestBody(phone, code)
        return authApi.verify(request)
    }
}