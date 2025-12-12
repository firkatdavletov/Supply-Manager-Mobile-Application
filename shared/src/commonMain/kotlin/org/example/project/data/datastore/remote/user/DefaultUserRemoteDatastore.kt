package org.example.project.data.datastore.remote.user

import org.example.project.data.api.user_api.UserApi
import org.example.project.data.api.user_api.model.DeleteUserResponseBody
import org.example.project.data.api.user_api.model.GetUserResponseBody
import org.example.project.data.api.user_api.model.LogoutResponseBody
import org.example.project.data.api.user_api.model.UpdateUserRequestBody
import org.example.project.data.api.user_api.model.UpdateUserResponseBody

class DefaultUserRemoteDatastore(
    private val userApi: UserApi,
): UserRemoteDataStore {
    override suspend fun getUser(): GetUserResponseBody {
        return userApi.getUser()
    }

    override suspend fun deleteUser(): DeleteUserResponseBody {
        return userApi.deleteUser()
    }

    override suspend fun updateUser(body: UpdateUserRequestBody): UpdateUserResponseBody {
        return userApi.updateUser(body)
    }

    override suspend fun logout(): LogoutResponseBody {
        return userApi.logout()
    }
}