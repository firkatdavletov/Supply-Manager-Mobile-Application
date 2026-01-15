package org.example.project.features.home

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.models.UserModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.repositories.CatalogRepository
import org.example.project.domain.repositories.OrderRepository
import org.example.project.domain.repositories.UserRepository
import org.example.project.domain.usecase.cart.AddToCartUseCase
import org.example.project.domain.usecase.cart.RemoveFromCartUseCase
import org.example.project.domain.usecase.order.GetCurrentOrderUseCase
import org.example.project.features.SnackBarManager
import org.example.project.features.mapper.OrderUIModelMapper

class DefaultHomeComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val homeCallbacks: HomeCallbacks,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val cartRepository: CartRepository,
    private val catalogRepository: CatalogRepository,
    private val getCurrentOrderUseCase: GetCurrentOrderUseCase,
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    orderUIModelMapper: OrderUIModelMapper,
): HomeComponent (
    componentContext = componentContext,
    snackBarManager = snackBarManager,
    initialState = HomeViewState(
        userName = null,
        categories = emptyList(),
        amount = 0.0,
        deliveryType = DeliveryType.DELIVERY,
        deliveryInfo = "",
        deliveryAddress = "",
        cartDepartment = null,
        currentOrders = emptyList(),
        storeIsClosed = false,
    ),
    reducer = HomeReducer(orderUIModelMapper),
) {
    private var job: Job? = null
    private var _user: UserModel? = null

    override fun onEvent(event: HomeViewEvent) {
        when (event) {
            HomeViewEvent.OnCartButtonClicked -> {
                homeCallbacks.navigateToCart()
            }
            is HomeViewEvent.OnCategoriesLoaded -> {
                reduce(event)
            }
            is HomeViewEvent.OnAddressClicked -> {
                homeCallbacks.navigateToMap()
            }
            is HomeViewEvent.OnCategoryClicked -> {
                homeCallbacks.navigateToCategory(event.categoryId, event.categoryTitle)
            }
            is HomeViewEvent.OnCartLoaded -> reduce(event)
            is HomeViewEvent.OnError -> {
                reduce(event)
                showError(event.text)
            }
            is HomeViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
            is HomeViewEvent.OnAddToCart -> addToCart(event.product)
            is HomeViewEvent.OnRemoveFromCart -> removeFromCart(event.product)
            is HomeViewEvent.OnCurrentOrderLoaded -> {
                reduce(event)
            }
            is HomeViewEvent.OnProfileClicked -> {
                if (_user != null) {
                    homeCallbacks.navigateToProfile()
                } else {
                    homeCallbacks.navigateToAuthorization()
                }
            }

            is HomeViewEvent.OnOrderClicked -> {
                homeCallbacks.navigateToOrder(event.id)
            }

            is HomeViewEvent.OnUserLoaded -> {
                reduce(event)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        subscribeToSubjects()
        getCurrentOrders()
    }

    private fun getCurrentOrders() {
        coroutineScope.launch {
            getCurrentOrderUseCase.invoke(Unit)
                .catch {  }
                .collect {
                    withContext(Dispatchers.Main) {
                        onEvent(HomeViewEvent.OnCurrentOrderLoaded(it))
                    }
                }
        }
    }

    private fun subscribeToSubjects() {
        coroutineScope.launch {
            combine(catalogRepository.catalogSubject, cartRepository.cartSubject) { catalog, cart ->
                Pair(catalog, cart)
            }.collect { data ->
                withContext(Dispatchers.Main) {
                    onEvent(HomeViewEvent.OnCategoriesLoaded(data.first, data.second.items))
                    onEvent(HomeViewEvent.OnCartLoaded(data.second))
                }
            }
        }
        coroutineScope.launch {
            orderRepository.ordersSubject.collect { orders ->
                withContext(Dispatchers.Main) {
                    onEvent(HomeViewEvent.OnCurrentOrderLoaded(orders))
                }
            }
        }
        coroutineScope.launch {
            userRepository.userSubject.collect {  userModel ->
                _user = userModel
                withContext(Dispatchers.Main) {
                    onEvent(HomeViewEvent.OnUserLoaded(userModel))
                }
            }
        }
    }

    private fun addToCart(product: ProductModel) {
        val params = AddToCartUseCase.Params(
            product = product.copy(count = product.count + 1)
        )
        job?.cancel()
        job = coroutineScope.launch {
            addToCartUseCase.invoke(params)
                .catch {
                    withContext(Dispatchers.Main) {
                        showThrowError(it)
                    }
                }
                .collect { resultModel ->
                    if (resultModel is ResultModel.Error) {
                        withContext(Dispatchers.Main) {
                            showError(resultModel.message)
                        }
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
            removeFromCartUseCase.invoke(params)
                .catch {
                    withContext(Dispatchers.Main) {
                        showThrowError(it)
                    }
                }
                .collect { resultModel ->
                    if (resultModel is ResultModel.Error) {
                        withContext(Dispatchers.Main) {
                            showError(resultModel.message)
                        }
                    }
                }
        }
    }
}