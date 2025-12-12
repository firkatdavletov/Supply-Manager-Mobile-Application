package org.example.project.data.repositories.token

import org.example.project.data.datastore.local.SecurityStorage
import org.example.project.domain.repositories.TokenRepository

class DefaultTokenRepository(
    private val securityStorage: SecurityStorage,
): TokenRepository {
    override fun saveAccessToken(token: String) {
        securityStorage.saveAccessToken(token)
    }

    override fun saveRefreshToken(token: String) {
        securityStorage.saveRefreshToken(token)
    }

    override fun getAccessToken(): String {
        return securityStorage.getAccessToken()
    }

    override fun getRefreshToken(): String {
        return securityStorage.getRefreshToken()
    }
}