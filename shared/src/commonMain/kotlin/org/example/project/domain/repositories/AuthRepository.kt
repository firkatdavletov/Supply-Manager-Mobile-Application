package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.AuthTypeModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.models.VerifyPhoneNumberModel

interface AuthRepository {
    val updates: Flow<Boolean>

    fun getAuthTypes(): Flow<ResultModel<List<AuthTypeModel>>>

    fun verifyPhoneNumber(
        phone: String,
        type: String,
    ): Flow<ResultModel<VerifyPhoneNumberModel>>

    fun verifyCode(
        phone: String,
        code: String,
    ): Flow<Boolean>

    fun loginByEmail(
        email: String,
        password: String,
    ): Flow<Boolean>

    suspend fun connect(checkId: String)

    suspend fun disconnect()
}
