package org.example.project.data.api.order_api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.data.api.order_api.model.CreateOrderRequestBody
import org.example.project.data.api.order_api.model.CreateOrderResponseModel
import org.example.project.data.api.order_api.model.GetCurrentOrdersResponseBody
import org.example.project.data.api.order_api.model.GetOrderByIdRequestBody
import org.example.project.data.api.order_api.model.GetOrderResponse
import org.example.project.data.api.order_api.model.GetOrdersResponseModel
import org.example.project.data.api.order_api.model.OrderStatusUpdateEntity

class OrderApiImpl(
    private val httpClient: HttpClient,
    private val wsClient: HttpClient,
) : OrderApi {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var wsSession: WebSocketSession? = null
    private var reconnectJob: Job? = null
    private val json = Json { ignoreUnknownKeys = true }

    private val _updates = MutableSharedFlow<OrderStatusUpdateEntity>()
    override val updates: SharedFlow<OrderStatusUpdateEntity> = _updates

    override suspend fun getOrderById(body: GetOrderByIdRequestBody): GetOrderResponse {
        val response = httpClient.get("orders/order") {
            url {
                parameters.append("id", body.id.toString())
            }
        }
        return response.body()
    }

    override suspend fun currentOrders(): GetCurrentOrdersResponseBody {
        return httpClient.get("orders/current").body()
    }

    override suspend fun getOrders(): GetOrdersResponseModel {
        return httpClient.get("orders").body()
    }

    override suspend fun getOrdersHistory(): GetOrdersResponseModel {
        return httpClient.get("orders/history").body()
    }

    override suspend fun createOrder(body: CreateOrderRequestBody): CreateOrderResponseModel {
        val response = httpClient.post {
            url {
                path("orders/createOrder")
            }
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return response.body()
    }

    override suspend fun takeOrder(id: Long): GetOrderResponse {
        val response = httpClient.get {
            url {
                path("orders/takeOrder")
                parameters.append("id", id.toString())
            }
        }

        return response.body()
    }

    override suspend fun completeOrder(id: Long): GetOrderResponse {
        val response = httpClient.get {
            url {
                path("orders/completeOrder")
                parameters.append("id", id.toString())
            }
        }

        return response.body()
    }

    override suspend fun cancelOrder(id: Long): GetOrderResponse {
        val response = httpClient.get {
            url {
                path("orders/cancelOrder")
                parameters.append("id", id.toString())
            }
        }

        return response.body()
    }

    override suspend fun pendingOrder(id: Long): GetOrderResponse {
        val response = httpClient.get {
            url {
                path("orders/pendingOrder")
                parameters.append("id", id.toString())
            }
        }

        return response.body()
    }

    override suspend fun connect() {
        if (reconnectJob != null) return
        reconnectJob = scope.launch {
            while (isActive) {
                try {
                    wsClient.webSocket("wss://foodbox-service-firkat.amvera.io/ws/orders") {
                        wsSession = this
                        send("subscribe")
                        listenIncomingMessages()
                    }
                } catch (e: Exception) {
                    println("WebSocket reconnect in 3 sec: ${e.message}")
                    delay(5000)
                }
            }
        }
    }

    private suspend fun listenIncomingMessages() {
        for (frame in wsSession!!.incoming) {
            when (frame) {
                is Frame.Text -> {
                    val text = frame.readText()
                    println("WS message: $text")
                    try {
                        val update = json.decodeFromString<OrderStatusUpdateEntity>(text)
                        _updates.emit(update)
                    } catch (_: Exception) {
                        println("Unknown WS message: $text")
                    }
                }

                is Frame.Pong -> {
                }

                else -> {
                    Unit
                }
            }
        }
    }

    override suspend fun disconnect() {
        wsSession?.send("unsubscribe")
        wsSession?.close()
        wsSession = null
        reconnectJob?.cancel()
        reconnectJob = null
    }
}