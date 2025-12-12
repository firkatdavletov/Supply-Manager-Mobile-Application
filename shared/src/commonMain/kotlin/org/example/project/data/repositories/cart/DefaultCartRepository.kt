package org.example.project.data.repositories.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import org.example.project.data.api.auth_api.model.CreateCartRequestBody
import org.example.project.data.api.cart_api.model.UpdateCartAddressRequestBody
import org.example.project.data.datastore.local.SecurityStorage
import org.example.project.data.datastore.remote.cart.RemoteCartDataStore
import org.example.project.data.entities.AddressEntity
import org.example.project.data.entities.CityEntity
import org.example.project.domain.models.ResultModel
import org.example.project.data.mapper.AddressModelMapper
import org.example.project.data.mapper.CartMapper
import org.example.project.data.mapper.DeliveryInfoMapper
import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.CartItemModel
import org.example.project.domain.models.CartModel
import org.example.project.domain.models.DeliveryInfoModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.ProductModel
import org.example.project.domain.repositories.CartRepository

class DefaultCartRepository(
    private val securityStorage: SecurityStorage,
    private val remoteCartDataStore: RemoteCartDataStore,
    private val cartMapper: CartMapper,
    private val addressModelMapper: AddressModelMapper,
    private val deliveryInfoMapper: DeliveryInfoMapper,
): CartRepository {

    private val _cartSubject = MutableSharedFlow<CartModel>(replay = 1)

    override val cartSubject: SharedFlow<CartModel> = _cartSubject.asSharedFlow()

    override fun loadCart(): Flow<ResultModel<Boolean>> {
        return flow {
            emit(ResultModel.Loading)
            val result = remoteCartDataStore.getCart()
            if (result.success && result.cart != null) {
                val cartModel = cartMapper.toModel(result.cart)
                _cartSubject.emit(cartModel)
                emit(ResultModel.Success(true))
            } else {
                emit(ResultModel.Error(result.error, result.code))
            }
        }
    }

    override fun createCart(
        deliveryType: DeliveryType,
        deliveryAddress: AddressModel?,
        departmentId: Int,
        deliveryInfo: DeliveryInfoModel?
    ): Flow<ResultModel<Boolean>> {
        val deviceId = securityStorage.getDeviceId()
        val newAddress = deliveryAddress?.let {
            AddressEntity(
                street = deliveryAddress.street,
                house = deliveryAddress.house,
                entrance = deliveryAddress.entrance,
                flat = deliveryAddress.flat,
                intercome = deliveryAddress.intercome,
                comment = deliveryAddress.comment,
                city = CityEntity(
                    id = deliveryAddress.city.id,
                    name = deliveryAddress.city.name,
                    subCities = emptyList(),
                    latitude = deliveryAddress.city.latitude,
                    longitude = deliveryAddress.city.longitude
                ),
                latitude = deliveryAddress.latitude,
                longitude = deliveryAddress.longitude
            )
        }
        val body = CreateCartRequestBody(
            deviceId = deviceId,
            deliveryType = deliveryType,
            deliveryAddress = newAddress,
            departmentId = departmentId,
            deliveryPrice = deliveryInfo?.deliveryPrice ?: 0.0,
            freeDeliveryPrice = deliveryInfo?.freeDeliveryPrice
        )
        return flow {
            emit(ResultModel.Loading)
            val response = remoteCartDataStore.createCart(body)

            if (response.success && response.token != null) {
                securityStorage.saveCartToken(response.token)
                emit(ResultModel.Success(true))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    override fun updateQuantity(product: ProductModel): Flow<ResultModel<Boolean>> {
        return flow {
            emit(ResultModel.Loading)
            updateLocalCart(product)
            val result = remoteCartDataStore.updateQuantity(productId = product.id, quantity = product.count)

            if (result.success && result.cart != null) {
                _cartSubject.tryEmit(cartMapper.toModel(result.cart))
                emit(ResultModel.Success(true))
            } else {
                emit(ResultModel.Error(result.error, result.code))
            }
        }
    }

    override fun updateDeliveryAddress(
        deliveryType: DeliveryType,
        deliveryAddress: AddressModel?,
        departmentId: Int,
        deliveryInfo: DeliveryInfoModel,
        comment: String?
    ): Flow<ResultModel<Boolean>> {
        return flow {
            val request = UpdateCartAddressRequestBody(
                deliveryType = deliveryType,
                deliveryAddress = deliveryAddress?.let { addressModelMapper.toEntity(it) },
                departmentId = departmentId,
                deliveryInfo = deliveryInfoMapper.toEntity(deliveryInfo),
                comment = comment,
            )
            val response = remoteCartDataStore.updateCartAddress(request)

            if (response.success && response.cart != null) {
                _cartSubject.tryEmit(cartMapper.toModel(response.cart))
                emit(ResultModel.Success(true))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }

    private fun updateLocalCart(product: ProductModel) {
        val cart = _cartSubject.replayCache.firstOrNull() ?: return
        val updateCartItem = cart.items.firstOrNull { it.productId == product.id }

        val updatedItems = if (updateCartItem != null) {
            if (product.count > 0) {
                cart.items.map {
                    if (it.productId == updateCartItem.productId) {
                        it.copy(quantity = product.count)
                    } else {
                        it
                    }
                }
            } else {
                cart.items.filter { it.productId != updateCartItem.productId }
            }
        } else {
            cart.items + CartItemModel(
                productId = product.id,
                title = product.title,
                quantity = 1,
                price = product.price
            )
        }

        val totalPrice = updatedItems.sumOf { it.price.toDouble() * it.quantity } + cart.deliveryInfo.deliveryPrice

        val updatedCart = cart.copy(
            items = updatedItems,
            totalPrice = totalPrice
        )
        _cartSubject.tryEmit(updatedCart)
    }

    override fun removeAll(): Flow<ResultModel<Boolean>> {
        return flow {
            val response = remoteCartDataStore.removeAll()

            if (response.success && response.cart != null) {
                val cartModel = cartMapper.toModel(response.cart)
                _cartSubject.emit(cartModel)
                emit(ResultModel.Success(true))
            } else {
                emit(ResultModel.Error(response.error, response.code))
            }
        }
    }
}