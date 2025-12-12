package org.example.project.domain.repositories

interface TokenRepository {
    fun saveAccessToken(token: String)
    fun saveRefreshToken(token: String)
    fun getAccessToken(): String
    fun getRefreshToken(): String
}