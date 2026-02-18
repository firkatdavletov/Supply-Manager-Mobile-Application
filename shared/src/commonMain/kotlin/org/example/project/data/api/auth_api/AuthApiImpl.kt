package org.example.project.data.api.auth_api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.data.api.auth_api.model.CheckSmsCodeRequestBody
import org.example.project.data.api.auth_api.model.CheckSmsCodeResponseBody
import org.example.project.data.api.auth_api.model.CreateCartRequestBody
import org.example.project.data.api.auth_api.model.CreateCartResponse
import org.example.project.data.api.auth_api.model.GetAuthTypesResponseBody
import org.example.project.data.api.auth_api.model.LoginByEmailRequestBody
import org.example.project.data.api.auth_api.model.LoginByEmailResponseBody
import org.example.project.data.api.auth_api.model.RefreshTokenRequestBody
import org.example.project.data.api.auth_api.model.RefreshTokenResponseBody
import org.example.project.data.api.auth_api.model.VerifyPhoneNumberRequestBody
import org.example.project.data.api.auth_api.model.VerifyPhoneNumberResponseBody
import org.example.project.data.entities.TokenPairEntity

class AuthApiImpl(private val httpClient: HttpClient, val wsClient: HttpClient) : AuthApi {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var wsSession: WebSocketSession? = null
    private var reconnectJob: Job? = null
    private val json = Json { ignoreUnknownKeys = true }
    private val _updates = MutableSharedFlow<TokenPairEntity>()
    override val updates: SharedFlow<TokenPairEntity> = _updates

    override suspend fun getAuthTypes(): GetAuthTypesResponseBody {
        return httpClient.get("auth/authTypes").body()
    }

    override suspend fun verifyPhoneNumber(
        verifyPhoneNumberRequestBody: VerifyPhoneNumberRequestBody,
    ): VerifyPhoneNumberResponseBody {
        return httpClient
            .post("auth/verifyPhoneNumber") {
                contentType(ContentType.Application.Json)
                setBody(verifyPhoneNumberRequestBody)
            }.body()
    }

    override suspend fun checkSmsCode(body: CheckSmsCodeRequestBody): CheckSmsCodeResponseBody {
        return httpClient
            .post("auth/checkSmsCode") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.body()
    }

    override suspend fun loginByEmail(body: LoginByEmailRequestBody): LoginByEmailResponseBody {
        return httpClient
            .post("/auth/loginByEmail") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.body()
    }

    override suspend fun refreshTokens(refreshTokenRequestBody: RefreshTokenRequestBody): RefreshTokenResponseBody {
        return httpClient
            .post("auth/refreshTokens") {
                contentType(ContentType.Application.Json)
                setBody(refreshTokenRequestBody)
            }.body()
    }

    override suspend fun createCart(body: CreateCartRequestBody): CreateCartResponse {
        return httpClient
            .post("auth/createCart") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.body()
    }

    override suspend fun connect(checkId: String) {
        if (reconnectJob != null) return

        reconnectJob = scope.launch {
            while (isActive) {
                try {
                    wsClient.webSocket("wss://foodbox-service-firkat.amvera.io/ws/callcheck/$checkId") {
                        wsSession = this
                        send("subscribe")
                        listenIncomingMessages()
                    }
                } catch (e: CancellationException) {
                    println("WebSocket cancelled")
                    throw e
                } catch (e: Exception) {
                    println("WebSocket reconnect in 3 sec: ${e.message}")
                    delay(3000)
                }
            }
        }
    }

    override suspend fun disconnect() {
        reconnectJob?.cancelAndJoin()
        reconnectJob = null

        wsSession?.send("unsubscribe")
        wsSession?.close()
        wsSession = null
    }

    private suspend fun listenIncomingMessages() {
        for (frame in wsSession!!.incoming) {
            when (frame) {
                is Frame.Text -> {
                    val text = frame.readText()
                    println("WS message: $text")

                    if (text == "pending") return

                    try {
                        val tokenPairEntity = json.decodeFromString<TokenPairEntity>(text)
                        _updates.emit(tokenPairEntity)
                    } catch (_: Exception) {
                        println("Unknown WS message: $text")
                    }
                }

                is Frame.Pong -> {}

                else -> {
                    Unit
                }
            }
        }
    }
}