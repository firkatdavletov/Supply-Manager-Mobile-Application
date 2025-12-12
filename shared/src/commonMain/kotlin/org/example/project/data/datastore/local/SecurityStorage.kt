package org.example.project.data.datastore.local

interface SecurityStorage {
    fun saveCartToken(token: String)
    fun saveAccessToken(token: String)
    fun saveRefreshToken(token: String)
    fun getCartToken(): String
    fun getAccessToken(): String
    fun getRefreshToken(): String
    fun getDeviceId(): String
}