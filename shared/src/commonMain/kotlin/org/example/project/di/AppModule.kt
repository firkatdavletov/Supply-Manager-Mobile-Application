package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.example.project.data.HttpException
import org.example.project.data.api.auth_api.AuthApi
import org.example.project.data.api.auth_api.AuthApiImpl
import org.example.project.data.api.auth_api.model.RefreshTokenRequestBody
import org.example.project.data.api.cart_api.CartApi
import org.example.project.data.api.cart_api.CartApiImpl
import org.example.project.data.api.catalog.CatalogApi
import org.example.project.data.api.catalog.CatalogApiImpl
import org.example.project.data.api.departments_api.DepartmentApi
import org.example.project.data.api.departments_api.DepartmentApiImpl
import org.example.project.data.api.map_api.MapApi
import org.example.project.data.api.map_api.MapApiImpl
import org.example.project.data.api.order_api.OrderApi
import org.example.project.data.api.order_api.OrderApiImpl
import org.example.project.data.api.payment_api.PaymentApi
import org.example.project.data.api.payment_api.PaymentApiImpl
import org.example.project.data.api.security.AuthPlugin
import org.example.project.data.api.user_api.UserApi
import org.example.project.data.api.user_api.UserApiImpl
import org.example.project.data.datastore.local.DefaultDepartmentLocalDataStore
import org.example.project.data.datastore.local.DepartmentsLocalDataStore
import org.example.project.data.datastore.local.SecurityStorage
import org.example.project.data.datastore.local.catalog.DefaultLocalCatalogDataStore
import org.example.project.data.datastore.local.catalog.LocalCatalogDataStore
import org.example.project.data.datastore.local.user.DefaultUserLocalDataStore
import org.example.project.data.datastore.local.user.UserLocalDataStore
import org.example.project.data.datastore.remote.auth.AuthRemoteDataStore
import org.example.project.data.datastore.remote.auth.DefaultAuthRemoteDatStore
import org.example.project.data.datastore.remote.cart.DefaultRemoteCartDataStore
import org.example.project.data.datastore.remote.cart.RemoteCartDataStore
import org.example.project.data.datastore.remote.catalog.CatalogRemoteDataStore
import org.example.project.data.datastore.remote.catalog.DefaultCatalogRemoteDataStore
import org.example.project.data.datastore.remote.departments.DefaultDepartmentsRemoteDataStore
import org.example.project.data.datastore.remote.departments.DepartmentsRemoteDataStore
import org.example.project.data.datastore.remote.geo.DefaultGeoRemoteDatasource
import org.example.project.data.datastore.remote.geo.GeoRemoteDatasource
import org.example.project.data.datastore.remote.order.DefaultOrderRemoteDataStore
import org.example.project.data.datastore.remote.order.OrderRemoteDataStore
import org.example.project.data.datastore.remote.payment.DefaultPaymentRemoteDataStore
import org.example.project.data.datastore.remote.payment.PaymentRemoteDataStore
import org.example.project.data.datastore.remote.user.DefaultUserRemoteDatastore
import org.example.project.data.datastore.remote.user.UserRemoteDataStore
import org.example.project.data.mapper.AddressModelMapper
import org.example.project.data.mapper.AuthTypeMapper
import org.example.project.data.mapper.BankInfoMapper
import org.example.project.data.mapper.CartItemMapper
import org.example.project.data.mapper.CartMapper
import org.example.project.data.mapper.CategoryMapper
import org.example.project.data.mapper.CityMapper
import org.example.project.data.mapper.DeliveryInfoMapper
import org.example.project.data.mapper.DepartmentMapper
import org.example.project.data.mapper.GeoAddressMapper
import org.example.project.data.mapper.OrderItemMapper
import org.example.project.data.mapper.OrderMapper
import org.example.project.data.mapper.PaymentMapper
import org.example.project.data.mapper.ProductMapper
import org.example.project.data.mapper.TokenPairMapper
import org.example.project.data.mapper.UserMapper
import org.example.project.data.mapper.WorkingHoursMapper
import org.example.project.data.repositories.auth.DefaultAuthRepository
import org.example.project.data.repositories.cart.DefaultCartRepository
import org.example.project.data.repositories.catalog.DefaultCatalogRepository
import org.example.project.data.repositories.departments.DefaultDepartmentRepository
import org.example.project.data.repositories.geo.DefaultGeoRepository
import org.example.project.data.repositories.order.DefaultOrderRepository
import org.example.project.data.repositories.payment.DefaultPaymentRepository
import org.example.project.data.repositories.sbp_banks.DefaultSbpBanksRepository
import org.example.project.data.repositories.token.DefaultTokenRepository
import org.example.project.data.repositories.user.DefaultUserRepository
import org.example.project.domain.repositories.AuthRepository
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.repositories.DepartmentsRepository
import org.example.project.domain.repositories.GeoRepository
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.repositories.PaymentRepository
import org.example.project.domain.repositories.SbpBanksRepository
import org.example.project.domain.repositories.TokenRepository
import org.example.project.domain.repositories.UserRepository
import org.example.project.domain.usecase.auth.GetAccessTokenUseCase
import org.example.project.domain.usecase.auth.GetAuthTypesUseCase
import org.example.project.domain.usecase.auth.VerifyCodeUseCase
import org.example.project.domain.usecase.auth.VerifyPhoneNumberUseCase
import org.example.project.domain.usecase.cart.AddToCartUseCase
import org.example.project.domain.usecase.cart.ClearCartUseCase
import org.example.project.domain.usecase.cart.CreateCartUseCase
import org.example.project.domain.usecase.cart.LoadCartUseCase
import org.example.project.domain.usecase.cart.RemoveFromCartUseCase
import org.example.project.domain.usecase.cart.UpdateDeliveryAddressUseCase
import org.example.project.domain.usecase.catalog.GetCategoriesUseCase
import org.example.project.domain.usecase.catalog.GetCategoryUseCase
import org.example.project.domain.usecase.catalog.GetProductUseCase
import org.example.project.domain.usecase.catalog.GetProductsUseCase
import org.example.project.domain.usecase.catalog.GetRemoteCategoriesUseCase
import org.example.project.domain.usecase.departments.GetDepartmentsUseCase
import org.example.project.domain.usecase.geo.GetGeoAddressUseCase
import org.example.project.domain.usecase.geo.SearchAddressUseCase
import org.example.project.domain.usecase.order.CancelOrderUseCase
import org.example.project.domain.usecase.order.CompleteOrderUseCase
import org.example.project.domain.usecase.order.CreateOrderUseCase
import org.example.project.domain.usecase.order.GetCurrentOrderUseCase
import org.example.project.domain.usecase.order.GetOrderByIdUseCase
import org.example.project.domain.usecase.order.GetOrdersUseCase
import org.example.project.domain.usecase.order.PendingOrderUseCase
import org.example.project.domain.usecase.order.TakeOrderUseCase
import org.example.project.domain.usecase.payment.GetPaymentTypesUseCase
import org.example.project.domain.usecase.sbp_banks.GetSbpBanksUseCase
import org.example.project.domain.usecase.user.DeleteUserUseCase
import org.example.project.domain.usecase.user.LoadUserUseCase
import org.example.project.domain.usecase.user.LogoutUserUseCase
import org.example.project.domain.usecase.user.UpdateUserUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.mapper.OrderUIModelMapper
import org.koin.core.qualifier.named
import org.koin.dsl.module

// For IOS:
// private const val BASE_URL = "localhost:8080"

// For Android Studio
// private const val BASE_URL = "10.0.2.2:8080"
// For remote server
private const val BASE_URL = "foodbox-service-firkat.amvera.io"
private val isHttps = true

@OptIn(ExperimentalSerializationApi::class)
fun appModule() =
    module {
        single<SnackBarManager> { SnackBarManager() }
        // Data stores
        single<AuthRemoteDataStore> { DefaultAuthRemoteDatStore(get()) }
        single<UserRemoteDataStore> { DefaultUserRemoteDatastore(get()) }
        single<UserLocalDataStore> { DefaultUserLocalDataStore() }
        single<CatalogRemoteDataStore> { DefaultCatalogRemoteDataStore(get()) }
        single<LocalCatalogDataStore> { DefaultLocalCatalogDataStore() }
        single<GeoRemoteDatasource> { DefaultGeoRemoteDatasource(get()) }
        single<RemoteCartDataStore> { DefaultRemoteCartDataStore(get(), get()) }
        single<DepartmentsRemoteDataStore> { DefaultDepartmentsRemoteDataStore(get()) }
        single<DepartmentsLocalDataStore> { DefaultDepartmentLocalDataStore() }
        single<PaymentRemoteDataStore> { DefaultPaymentRemoteDataStore(get()) }
        single<OrderRemoteDataStore> { DefaultOrderRemoteDataStore(get()) }

        // Repositories
        single<AuthRepository> { DefaultAuthRepository(get(), get(), get(), get()) }
        single<UserRepository> { DefaultUserRepository(get(), get(), get()) }
        single<CatalogRepository> { DefaultCatalogRepository(get(), get(), get(), get()) }
        single<TokenRepository> { DefaultTokenRepository(get()) }
        single<CartRepository> { DefaultCartRepository(get(), get(), get(), get(), get()) }
        single<GeoRepository> { DefaultGeoRepository(get(), get()) }
        single<DepartmentsRepository> { DefaultDepartmentRepository(get(), get(), get()) }
        single<PaymentRepository> { DefaultPaymentRepository(get(), get()) }
        single<SbpBanksRepository> { DefaultSbpBanksRepository(get(), get()) }
        single<OrderRepository> { DefaultOrderRepository(get(), get(), get(), get()) }

        // UseCases
        factory<LoadUserUseCase> { LoadUserUseCase(get()) }
        factory<GetAuthTypesUseCase> { GetAuthTypesUseCase(get()) }
        factory<GetAccessTokenUseCase> { GetAccessTokenUseCase(get()) }
        factory<VerifyPhoneNumberUseCase> { VerifyPhoneNumberUseCase(get()) }
        factory<VerifyCodeUseCase> { VerifyCodeUseCase(get()) }
        factory<GetCategoriesUseCase> { GetCategoriesUseCase(get()) }
        factory<GetProductsUseCase> { GetProductsUseCase(get()) }
        factory<AddToCartUseCase> { AddToCartUseCase(get()) }
        factory<LoadCartUseCase> { LoadCartUseCase(get()) }
        factory<RemoveFromCartUseCase> { RemoveFromCartUseCase(get()) }
        factory<GetGeoAddressUseCase> { GetGeoAddressUseCase(get()) }
        factory<UpdateDeliveryAddressUseCase> { UpdateDeliveryAddressUseCase(get()) }
        factory<GetDepartmentsUseCase> { GetDepartmentsUseCase(get(), get()) }
        factory<GetProductUseCase> { GetProductUseCase(get()) }
        factory<GetRemoteCategoriesUseCase> { GetRemoteCategoriesUseCase(get()) }
        factory<CreateOrderUseCase> { CreateOrderUseCase(get()) }
        factory<GetSbpBanksUseCase> { GetSbpBanksUseCase(get()) }
        factory<GetPaymentTypesUseCase> { GetPaymentTypesUseCase(get()) }
        factory<ClearCartUseCase> { ClearCartUseCase(get()) }
        factory<GetCurrentOrderUseCase> { GetCurrentOrderUseCase(get()) }
        factory<GetOrdersUseCase> { GetOrdersUseCase(get()) }
        factory<SearchAddressUseCase> { SearchAddressUseCase(get()) }
        factory<CreateCartUseCase> { CreateCartUseCase(get()) }
        factory<GetOrderByIdUseCase> { GetOrderByIdUseCase(get()) }
        factory<DeleteUserUseCase> { DeleteUserUseCase(get(), get(), get()) }
        factory<LogoutUserUseCase> { LogoutUserUseCase(get(), get(), get()) }
        factory<UpdateUserUseCase> { UpdateUserUseCase(get()) }
        factory { TakeOrderUseCase(get()) }
        factory { CompleteOrderUseCase(get()) }
        factory { CancelOrderUseCase(get()) }
        factory { PendingOrderUseCase(get()) }
        factory { GetCategoryUseCase(get()) }

        // Mappers
        factory<AuthTypeMapper> { AuthTypeMapper() }
        factory<UserMapper> { UserMapper() }
        factory<CategoryMapper> { CategoryMapper(get()) }
        factory<ProductMapper> { ProductMapper() }
        factory<CartMapper> { CartMapper(get(), get(), get(), get(), get()) }
        factory<CartItemMapper> { CartItemMapper() }
        factory { CityMapper() }
        factory { DeliveryInfoMapper() }
        factory { AddressModelMapper(get()) }
        factory { GeoAddressMapper(get(), get()) }
        factory { DepartmentMapper(get(), get()) }
        factory { WorkingHoursMapper() }
        factory { OrderMapper(get(), get()) }
        factory { OrderItemMapper() }
        factory { BankInfoMapper() }
        factory { PaymentMapper(get()) }
        factory { OrderUIModelMapper() }
        factory { TokenPairMapper() }

        single<HttpClient>(named("cart")) {
            val securityStorage: SecurityStorage = get()

            val httpClient = HttpClient {
                install(Logging) {
                    level = LogLevel.ALL // Уровень логирования (ALL, HEADERS, BODY, INFO, NONE)
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("Ktorfit Log: $message") // Логирование в консоль
                        }
                    }
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                        },
                    )
                }
                install(AuthPlugin()) {
                    tokenProvider = {
                        val token = securityStorage.getCartToken()
                        token
                    }
                }

                defaultRequest {
                    url {
                        if (isHttps) {
                            protocol = URLProtocol.HTTPS
                        }
                        host = BASE_URL
                    }
                }

//            install(HttpRequestRetry) {
//                retryOnServerErrors(maxRetries = 5)
//                exponentialDelay()
//            }
                expectSuccess = true
            }
            httpClient
        }

        single<HttpClient>(named("ws_orders")) {
            val securityStorage: SecurityStorage = get()
            val httpClient = HttpClient {
                install(Logging) {
                    level = LogLevel.ALL // Уровень логирования (ALL, HEADERS, BODY, INFO, NONE)
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("Ktorfit Log: $message") // Логирование в консоль
                        }
                    }
                }
                install(WebSockets) {
                    pingIntervalMillis = 20_000
                }
                install(AuthPlugin()) {
                    tokenProvider = {
                        val token = securityStorage.getAccessToken()
                        token
                    }
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                        },
                    )
                }
                expectSuccess = true
            }
            httpClient
        }

        single<HttpClient>(named("ws_callcheck")) {
            val httpClient = HttpClient {
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("Ktorfit Log: $message")
                        }
                    }
                }
                install(WebSockets) {
                    pingIntervalMillis = 5_000
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                        },
                    )
                }
                expectSuccess = true
            }
            httpClient
        }

        single<HttpClient>(named("auth")) {
            val securityStorage: SecurityStorage = get()
            val authApi: AuthApi = get()

            val httpClient = HttpClient {
                install(Logging) {
                    level = LogLevel.ALL // Уровень логирования (ALL, HEADERS, BODY, INFO, NONE)
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("Ktorfit Log: $message") // Логирование в консоль
                        }
                    }
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                        },
                    )
                }
                install(AuthPlugin()) {
                    tokenProvider = {
                        val token = securityStorage.getAccessToken()
                        token
                    }
                }

                defaultRequest {
                    url {
                        if (isHttps) {
                            protocol = URLProtocol.HTTPS
                        }
                        host = BASE_URL
                    }
                }

//            install(HttpRequestRetry) {
//                retryOnServerErrors(maxRetries = 2)
//                exponentialDelay()
//            }
                expectSuccess = true
            }
            httpClient.plugin(HttpSend).intercept { request ->
                val originalCall = execute(request)

                if (originalCall.response.status.value == HttpStatusCode.Unauthorized.value) {
                    val refreshToken = securityStorage.getRefreshToken()
                    if (refreshToken.isNotEmpty()) {
                        try {
                            val refreshResponse = authApi.refreshTokens(RefreshTokenRequestBody(refreshToken))

                            if (refreshResponse.success && refreshResponse.tokens != null) {
                                securityStorage.saveAccessToken(refreshResponse.tokens.access)
                                securityStorage.saveRefreshToken(refreshResponse.tokens.refresh)
                                execute(request)
                            } else {
                                securityStorage.saveAccessToken("")
                                securityStorage.saveRefreshToken("")
                                throw HttpException.RequiredAuth
                            }
                        } catch (e: Exception) {
                            securityStorage.saveAccessToken("")
                            securityStorage.saveRefreshToken("")
                            throw e
                        }
                    } else {
                        throw IllegalStateException("No refresh token available")
                    }
                    execute(request)
                } else {
                    originalCall
                }
            }
            httpClient
        }

        single<HttpClient>(named("no_auth")) {
            val httpClient = HttpClient {
                install(Logging) {
                    level = LogLevel.ALL // Уровень логирования (ALL, HEADERS, BODY, INFO, NONE)
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("Ktorfit Log: $message") // Логирование в консоль
                        }
                    }
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                        },
                    )
                }

                defaultRequest {
                    url {
                        if (isHttps) {
                            protocol = URLProtocol.HTTPS
                        }
                        host = BASE_URL
                    }
                }

                expectSuccess = true
            }
            httpClient
        }

        // Api
        single<AuthApi> {
            val httpClient = get<HttpClient>(named("no_auth"))
            val wsClient = get<HttpClient>(named("ws_callcheck"))
            AuthApiImpl(httpClient, wsClient)
        }

        single<UserApi> {
            val httpClient = get<HttpClient>(named("auth"))
            UserApiImpl(httpClient)
        }

        single<CatalogApi> {
            val httpClient = get<HttpClient>(named("no_auth"))
            CatalogApiImpl(httpClient)
        }

        single<CartApi> {
            val httpClient = get<HttpClient>(named("cart"))
            CartApiImpl(httpClient)
        }

        single<MapApi> {
            val httpClient = get<HttpClient>(named("no_auth"))
            MapApiImpl(httpClient)
        }

        single<DepartmentApi> {
            val httpClient = get<HttpClient>(named("no_auth"))
            DepartmentApiImpl(httpClient)
        }

        single<PaymentApi> {
            val httpClient = get<HttpClient>(named("auth"))
            PaymentApiImpl(httpClient)
        }

        single<OrderApi> {
            val httpClient = get<HttpClient>(named("no_auth"))
            val wsClient = get<HttpClient>(named("ws_orders"))
            OrderApiImpl(httpClient, wsClient)
        }
    }