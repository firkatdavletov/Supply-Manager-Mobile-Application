package org.example.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GeoAddressModel
import org.example.project.domain.models.ResultModel

interface GeoRepository {
    fun getAddress(query: String?, uri: String?, entrance: Int?): Flow<ResultModel<GeoAddressModel>>
    fun searchAddress(query: String): Flow<ResultModel<List<GeoAddressModel>>>
}