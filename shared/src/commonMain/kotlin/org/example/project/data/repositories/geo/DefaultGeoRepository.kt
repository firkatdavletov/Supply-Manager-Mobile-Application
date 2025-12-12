package org.example.project.data.repositories.geo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.data.datastore.remote.geo.GeoRemoteDatasource
import org.example.project.data.mapper.GeoAddressMapper
import org.example.project.domain.models.GeoAddressModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.GeoRepository

class DefaultGeoRepository(
    private val geoRemoteDatasource: GeoRemoteDatasource,
    private val geoAddressMapper: GeoAddressMapper,
): GeoRepository {
    override fun getAddress(query: String?, uri: String?, entrance: Int?): Flow<ResultModel<GeoAddressModel>> {
        return flow {
            emit(ResultModel.Loading)
            val response = geoRemoteDatasource.getAddress(query, uri, entrance)
            if (response.success && response.address != null) {
                val geoAddress = geoAddressMapper.toModel(response.address)
                emit(ResultModel.Success(geoAddress))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun searchAddress(query: String): Flow<ResultModel<List<GeoAddressModel>>> {
        return flow {
            emit(ResultModel.Loading)
            val response = geoRemoteDatasource.searchAddress(query)

            if (response.success && response.addresses != null) {
                val geoAddress = geoAddressMapper.toModel(response.addresses)
                emit(ResultModel.Success(geoAddress))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }
}