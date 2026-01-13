package org.example.project.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.ResultModel
import org.example.project.domain.models.VerifyPhoneNumberModel
import org.example.project.domain.repositories.AuthRepository
import org.example.project.domain.usecase.base.IOUseCase

class VerifyPhoneNumberUseCase(
    private val authRepository: AuthRepository,
): IOUseCase<VerifyPhoneNumberUseCase.Params, ResultModel<VerifyPhoneNumberModel>>() {
    override fun execute(param: Params): Flow<ResultModel<VerifyPhoneNumberModel>> {
        return authRepository.verifyPhoneNumber(param.phone, param.type)
    }

    class Params(val phone: String, val type: String)
}