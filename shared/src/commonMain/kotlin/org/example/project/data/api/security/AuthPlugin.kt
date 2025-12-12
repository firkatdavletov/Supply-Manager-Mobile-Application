package org.example.project.data.api.security

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.util.AttributeKey
import io.ktor.utils.io.InternalAPI

class AuthPlugin : HttpClientPlugin<AuthPlugin.Config, AuthPlugin> {

    class Config {
        lateinit var tokenProvider: () -> String
    }

    private lateinit var tokenProvider: () -> String

    override val key: AttributeKey<AuthPlugin> = AttributeKey("AuthPlugin")

    override fun prepare(block: Config.() -> Unit): AuthPlugin {
        val config = Config().apply(block)
        tokenProvider = config.tokenProvider
        return this
    }

    @OptIn(InternalAPI::class)
    override fun install(plugin: AuthPlugin, scope: HttpClient) {
        scope.requestPipeline.intercept(HttpRequestPipeline.State) {
            val token = tokenProvider()
            if (token.isNotBlank()) {
                print(token)
                context.headers.append("Authorization", "Bearer $token")
            }
            proceed()
        }
    }
}
