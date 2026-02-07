package org.example.project.data.datastore.remote.auth

import kotlinx.coroutines.flow.SharedFlow
import org.example.project.data.api.auth_api.AuthApi
import org.example.project.data.api.auth_api.model.CheckSmsCodeRequestBody
import org.example.project.data.api.auth_api.model.CheckSmsCodeResponseBody
import org.example.project.data.api.auth_api.model.GetAuthTypesResponseBody
import org.example.project.data.api.auth_api.model.VerifyPhoneNumberRequestBody
import org.example.project.data.api.auth_api.model.VerifyPhoneNumberResponseBody
import org.example.project.data.entities.TokenPairEntity

class DefaultAuthRemoteDatStore(
    private val authApi: AuthApi,
) : AuthRemoteDataStore {
    override val updates: SharedFlow<TokenPairEntity>
        get() = authApi.updates

    override suspend fun getAuthTypes(): GetAuthTypesResponseBody {
        return authApi.getAuthTypes()
    }

    override suspend fun verifyPhoneNumber(request: VerifyPhoneNumberRequestBody): VerifyPhoneNumberResponseBody {
        return authApi.verifyPhoneNumber(request)
    }

    override suspend fun checkSmsCode(
        phone: String,
        code: String,
    ): CheckSmsCodeResponseBody {
        val request = CheckSmsCodeRequestBody(phone, code)
        return authApi.checkSmsCode(request)
    }

    override suspend fun connect(checkId: String) {
        authApi.connect(checkId)
    }

    override suspend fun disconnect() {
        authApi.disconnect()
    }
}