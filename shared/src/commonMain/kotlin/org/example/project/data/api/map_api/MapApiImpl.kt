package org.example.project.data.api.map_api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class MapApiImpl(private val httpClient: HttpClient) : MapApi {

    override suspend fun getAddress(
        query: String,
    ): GetAddressResponseBody {
        return httpClient.get {
            url {
                path("map/query")
                parameters.append("query", query)
            }
        }.body()
    }

    override suspend fun getAddressByUri(uri: String, entrance: Int?): GetAddressResponseBody {
        return httpClient.get {
            url {
                path("map/uri")
                parameters.append("uri", uri)
                parameters.append("entrance", entrance.toString())
            }
        }.body()
    }

    override suspend fun searchAddress(
        query: String,
        sessionToken: String
    ): SearchAddressResponseBody {
        return httpClient.get {
            url {
                path("map/search")
                parameters.append("query", query)
                parameters.append("session", sessionToken)
            }
        }.body()
    }
}