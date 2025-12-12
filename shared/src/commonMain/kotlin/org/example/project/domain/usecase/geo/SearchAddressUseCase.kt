package org.example.project.domain.usecase.geo

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.models.GeoAddressModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.GeoRepository
import org.example.project.domain.usecase.base.IOUseCase

class SearchAddressUseCase(
    private val geoRepository: GeoRepository,
) : IOUseCase<String, ResultModel<List<GeoAddressModel>>>() {
    override fun execute(param: String): Flow<ResultModel<List<GeoAddressModel>>> {
        return geoRepository.searchAddress(query = param)
    }
}