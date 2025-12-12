package org.example.project.features.cart

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.example.project.data.datastore.local.SecurityStorage
import org.example.project.domain.models.CartItemModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.cart.AddToCartUseCase
import org.example.project.domain.usecase.cart.LoadCartUseCase
import org.example.project.domain.usecase.cart.RemoveFromCartUseCase
import org.example.project.domain.usecase.catalog.GetProductUseCase
import org.example.project.features.SnackBarManager

class DefaultCartComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: CartViewCallbacks,
    private val loadCartUseCase: LoadCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val cartRepository: CartRepository,
    private val securityStorage: SecurityStorage,
): CartComponent(
    componentContext = componentContext,
    initialState = CartViewState(
        totalPrice = 0,
        deliveryPrice = 0,
        productsPrice = 0,
        cartItems = emptyList(),
        deliveryType = DeliveryType.PICKUP,
        addressString = "",
        continueText = "ВЫБЕРИТЕ АДРЕС"
    ),
    snackBarManager = snackBarManager,
    reducer = CartReducer()
) {
    private var job: Job? = null

    init {
        subscribeToCart()
    }

    override fun initDataLoad() {
        getCart()
    }

    override fun onEvent(event: CartViewEvent) {
        when (event) {
            CartViewEvent.OnBackClick -> {
                callbacks.onBackClicked()
            }
            CartViewEvent.OnConfirmButtonClicked -> {
                if (securityStorage.getAccessToken().isBlank()) {
                    callbacks.navigateToLogin()
                } else {
                    callbacks.navigateToPayment()
                }
            }
            is CartViewEvent.OnCartLoaded -> reduce(event)
            is CartViewEvent.OnAddToCart -> addToCart(event.product)
            is CartViewEvent.OnRemoveFromCart -> removeFromCart(event.product)
            is CartViewEvent.OnError -> {
                reduce(event)
                showError(event.message)
            }
            is CartViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
        }
    }

    fun getCart() {
        coroutineScope.launch {
            loadCartUseCase.invoke(Unit)
                .catch {
                    onEvent(CartViewEvent.OnThrowError(it))
                }
                .collect {}
        }
    }

    private fun subscribeToCart() {
        coroutineScope.launch {
            cartRepository.cartSubject.collect {
                onEvent(CartViewEvent.OnCartLoaded(it))
            }
        }
    }

    private suspend fun getProduct(productId: Long, onSuccess: (ProductModel) -> Unit) {
        getProductUseCase.invoke(productId)
            .catch {
                onEvent(CartViewEvent.OnThrowError(it))
            }
            .collect {
                it?.let { onSuccess(it) }
            }
    }

    private fun addToCart(cartItemModel: CartItemModel) {
        println("on add to cart")
        coroutineScope.launch {
            getProduct(cartItemModel.productId) {
                addToCart(it.copy(count = cartItemModel.quantity))
            }
        }
    }

    private fun removeFromCart(cartItemModel: CartItemModel) {
        coroutineScope.launch {
            getProduct(cartItemModel.productId) {
                removeFromCart(it.copy(count = cartItemModel.quantity))
            }
        }
    }

    private fun addToCart(product: ProductModel) {
        val params = AddToCartUseCase.Params(
            product = product.copy(count = product.count + 1)
        )
        job?.cancel()
        job = coroutineScope.launch {
            println(" on add")
            addToCartUseCase.invoke(params)
                .catch {
                    onEvent(CartViewEvent.OnThrowError(it))
                }
                .collect {
                    if (it is ResultModel.Error) {
                        onEvent(CartViewEvent.OnError(it.message))
                    }
                }
        }
    }

    private fun removeFromCart(product: ProductModel) {
        val params = RemoveFromCartUseCase.Params(
            product = product.copy(count = product.count - 1)
        )
        job?.cancel()
        job = coroutineScope.launch {
            println(" on remove")
            removeFromCartUseCase.invoke(params)
                .catch {
                    onEvent(CartViewEvent.OnThrowError(it))
                }
                .collect {
                    if (it is ResultModel.Error) {
                        onEvent(CartViewEvent.OnError(it.message))
                    }
                }
        }
    }
}