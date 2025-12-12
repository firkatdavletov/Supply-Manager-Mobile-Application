package org.example.project.data.api.payment_api

import org.example.project.data.api.payment_api.model.GetBanksResponse
import org.example.project.data.api.payment_api.model.GetPaymentTypesResponseBody
import org.example.project.data.api.payment_api.model.PayOrderRequestBody
import org.example.project.data.entities.PaymentEntity

interface PaymentApi {

    suspend fun getPaymentTypes(): GetPaymentTypesResponseBody

    suspend fun create(body: PayOrderRequestBody): PaymentEntity

    suspend fun getQrBanks(): GetBanksResponse

    suspend fun getSubBanks(): GetBanksResponse
}