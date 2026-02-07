package org.example.project.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Config {
    @Serializable
    data object AppIntroduction : Config()
    @Serializable
    data class SelectAddress(val fromScreen: String?): Config()
    @Serializable
    data object Home: Config()
    @Serializable
    data object Launch: Config()

    @Serializable
    data object Cart: Config()
    @Serializable
    data object Payment: Config()

    @Serializable
    data class CurrentOrder(
        val fromScreen: String?,
        val orderId: Long
    ): Config()

    @Serializable
    data class Catalog(val categoryId: Int?): Config()

    @Serializable
    data object Profile : Config()

    @Serializable
    data class SignIn(
        val fromScreen: String?
    ) : Config()

    @Serializable
    data class Verification(
        val fromScreen: String?,
        val phoneNumber: String,
        val authType: String,
        val checkId: String?,
        val callPhone: String?,
    ) : Config()

    @Serializable
    data class SearchAddress(val fromScreen: String?) : Config()
}