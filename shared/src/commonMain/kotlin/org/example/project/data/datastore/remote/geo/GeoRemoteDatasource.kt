package org.example.project.data.datastore.remote.geo

import org.example.project.data.api.map_api.GetAddressResponseBody
import org.example.project.data.api.map_api.SearchAddressResponseBody

interface GeoRemoteDatasource {
    suspend fun getAddress(query: String?, uri: String?, entrance: Int?): GetAddressResponseBody
    suspend fun searchAddress(query: String): SearchAddressResponseBody
}