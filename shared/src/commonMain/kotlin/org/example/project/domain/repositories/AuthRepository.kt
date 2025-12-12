package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.AuthTypesModel
import org.example.project.domain.models.ResultModel

interface AuthRepository {
    fun getAuthTypes(): Flow<ResultModel<AuthTypesModel>>
    fun sendVerification(phone: String): Flow<Boolean>
    fun verifyCode(phone: String, code: String): Flow<Boolean>
}