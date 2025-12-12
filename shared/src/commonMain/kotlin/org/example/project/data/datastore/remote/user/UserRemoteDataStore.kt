package org.example.project.data.datastore.remote.user

import kotlinx.coroutines.flow.Flow
import org.example.project.data.api.user_api.model.DeleteUserResponseBody
import org.example.project.data.api.user_api.model.GetUserResponseBody
import org.example.project.data.api.user_api.model.LogoutResponseBody
import org.example.project.data.api.user_api.model.UpdateUserRequestBody
import org.example.project.data.api.user_api.model.UpdateUserResponseBody
import org.example.project.data.entities.UserEntity

interface UserRemoteDataStore {
    suspend fun getUser(): GetUserResponseBody
    suspend fun updateUser(body: UpdateUserRequestBody): UpdateUserResponseBody
    suspend fun deleteUser(): DeleteUserResponseBody
    suspend fun logout(): LogoutResponseBody
}