package org.example.project.data.api.user_api

import org.example.project.data.api.user_api.model.DeleteUserResponseBody
import org.example.project.data.api.user_api.model.GetUserResponseBody
import org.example.project.data.api.user_api.model.LogoutResponseBody
import org.example.project.data.api.user_api.model.UpdateUserRequestBody
import org.example.project.data.api.user_api.model.UpdateUserResponseBody

interface UserApi {
    suspend fun getUser(): GetUserResponseBody
    suspend fun updateUser(updateUserRequestBody: UpdateUserRequestBody): UpdateUserResponseBody
    suspend fun deleteUser(): DeleteUserResponseBody
    suspend fun logout(): LogoutResponseBody
}