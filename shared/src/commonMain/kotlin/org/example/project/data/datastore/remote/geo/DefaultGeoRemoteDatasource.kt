package org.example.project.data.datastore.remote.geo

import org.example.project.data.api.map_api.GetAddressResponseBody
import org.example.project.data.api.map_api.MapApi
import org.example.project.data.api.map_api.SearchAddressResponseBody
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DefaultGeoRemoteDatasource(
    private val mapApi: MapApi,
): GeoRemoteDatasource {
    @OptIn(ExperimentalUuidApi::class)
    private val sessionToken = Uuid.random().toString()

    override suspend fun getAddress(query: String?, uri: String?, entrance: Int?): GetAddressResponseBody {
        return if (query != null && uri == null) {
            mapApi.getAddress(query)
        } else if (query == null && uri != null) {
            mapApi.getAddressByUri(uri, entrance)
        } else {
            throw IllegalArgumentException()
        }
    }

    override suspend fun searchAddress(query: String): SearchAddressResponseBody {
        return mapApi.searchAddress(query, sessionToken)
    }
}