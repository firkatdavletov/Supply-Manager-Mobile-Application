package org.example.project.domain.usecase.geo

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GeoAddressModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.GeoRepository
import org.example.project.domain.usecase.base.IOUseCase

class GetGeoAddressUseCase(
    private val geoRepository: GeoRepository,
): IOUseCase<GetGeoAddressUseCase.Params, ResultModel<GeoAddressModel>>() {
    override fun execute(param: Params): Flow<ResultModel<GeoAddressModel>> {
        val query = if (param.longitude != null && param.latitude != null) "${param.longitude},${param.latitude}" else null
        return geoRepository.getAddress(query, param.uri, param.entrance)
    }

    class Params {
        val longitude: Double?
        val latitude: Double?
        val uri: String?
        val entrance: Int?

        constructor(longitude: Double, latitude: Double) {
            this.longitude = longitude
            this.latitude = latitude
            this.uri = null
            this.entrance = null
        }

        constructor(uri: String, entrance: Int?) {
            this.longitude = null
            this.latitude = null
            this.uri = uri
            this.entrance = entrance
        }
    }
}