package org.example.project.data.repositories.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.example.project.data.entities.AuthTypesEntity
import org.example.project.data.api.auth_api.model.SendSmsRequestBody
import org.example.project.data.datastore.local.SecurityStorage
import org.example.project.data.datastore.remote.auth.AuthRemoteDataStore
import org.example.project.data.mapper.AuthTypesMapper
import org.example.project.domain.models.AuthTypesModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.AuthRepository

class DefaultAuthRepository(
    private val authRemoteDataStore: AuthRemoteDataStore,
    private val securityStorage: SecurityStorage,
    private val authTypesMapper: AuthTypesMapper,
): AuthRepository {

    override fun getAuthTypes(): Flow<ResultModel<AuthTypesModel>> {
        return flow {
            emit(ResultModel.Loading)

            val response = authRemoteDataStore.getAuthTypes()

            if (response.success) {
                emit(ResultModel.Success(AuthTypesModel(response.types)))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun sendVerification(phone: String): Flow<Boolean> {
        return flow {
            val request = SendSmsRequestBody(phone)
            val response = authRemoteDataStore.sendVerificationCode(request)
            emit(response.success)
        }
    }

    override fun verifyCode(
        phone: String,
        code: String
    ): Flow<Boolean> {
        return flow {
            val response = authRemoteDataStore.verify(phone, code)

            if (response.success && response.tokens != null) {
                val accessToken = response.tokens.access
                val refreshToken = response.tokens.refresh

                securityStorage.saveAccessToken(accessToken)
                securityStorage.saveRefreshToken(refreshToken)

                emit(true)
            } else {
                emit(false)
            }
        }
    }

    private fun AuthTypesEntity.toModel(): AuthTypesModel {
        return AuthTypesModel(
            types = types
        )
    }
}