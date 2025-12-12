package org.example.project.data.api.map_api

interface MapApi {

    suspend fun getAddress(
        query: String,
    ): GetAddressResponseBody

    suspend fun getAddressByUri(uri: String, entrance: Int?): GetAddressResponseBody

    suspend fun searchAddress(
        query: String,
        sessionToken: String,
    ): SearchAddressResponseBody
}