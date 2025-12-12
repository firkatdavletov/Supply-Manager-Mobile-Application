package org.example.project.data.datastore.local.user

import kotlinx.coroutines.flow.Flow
import org.example.project.data.entities.UserEntity

interface UserLocalDataStore {
    fun getUser(): Flow<UserEntity?>
    fun saveUser(userEntity: UserEntity)
    fun deleteUser()
}