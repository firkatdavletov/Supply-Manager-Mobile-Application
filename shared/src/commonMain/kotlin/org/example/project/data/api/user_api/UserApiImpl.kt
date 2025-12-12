package org.example.project.data.api.user_api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.api.user_api.model.DeleteUserResponseBody
import org.example.project.data.api.user_api.model.GetUserResponseBody
import org.example.project.data.api.user_api.model.LogoutResponseBody
import org.example.project.data.api.user_api.model.UpdateUserRequestBody
import org.example.project.data.api.user_api.model.UpdateUserResponseBody

class UserApiImpl(private val httpClient: HttpClient) : UserApi {

    override suspend fun getUser(): GetUserResponseBody {
        return httpClient.get("user").body()
    }

    override suspend fun updateUser(updateUserRequestBody: UpdateUserRequestBody): UpdateUserResponseBody{
        return httpClient.post("user") {
            contentType(ContentType.Application.Json)
            setBody(updateUserRequestBody)
        }.body()
    }

    override suspend fun deleteUser(): DeleteUserResponseBody {
        return httpClient.delete("user").body()
    }

    override suspend fun logout(): LogoutResponseBody {
        return httpClient.get("user/logout").body()
    }
}