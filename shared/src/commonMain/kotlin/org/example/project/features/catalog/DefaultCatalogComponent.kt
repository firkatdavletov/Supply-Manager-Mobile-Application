package org.example.project.features.catalog

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.example.project.domain.models.ProductModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.cart.AddToCartUseCase
import org.example.project.domain.usecase.cart.LoadCartUseCase
import org.example.project.domain.usecase.cart.RemoveFromCartUseCase
import org.example.project.domain.usecase.catalog.GetProductsUseCase

class DefaultCatalogComponent(
    componentContext: ComponentContext,
    title: String,
    private val categoryId: Long,
    private val callbacks: CatalogCallbacks,
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val cartRepository: CartRepository
): CatalogComponent (
    componentContext = componentContext,
    initialState = CatalogViewState(
        title = title,
        products = emptyList(),
        amount = 0.0,
        freeDeliveryPrice = null,
        productsPrice = 0.0
    ),
    reducer = CatalogReducer(),) {
    private var job: Job? = null

    override fun onResume() {
        coroutineScope.launch {
            getProductsUseCase.invoke(categoryId)
                .collect {
                    onEvent(CatalogViewEvent.OnProductsLoaded(it))
                    subscribeToCart()
                }
        }
    }

    override fun onEvent(event: CatalogViewEvent) {
        when (event) {
            CatalogViewEvent.OnAddressClicked -> TODO()
            CatalogViewEvent.OnBackClicked -> {
                callbacks.onBack()
            }
            is CatalogViewEvent.OnProductsLoaded -> reduce(event)
            is CatalogViewEvent.OnCategoryClicked -> TODO()
            is CatalogViewEvent.OnUserLoaded -> TODO()
            is CatalogViewEvent.OnCategoryLoaded -> TODO()
            is CatalogViewEvent.OnAddToCart -> {
                addToCart(event.product)
            }

            is CatalogViewEvent.OnRemoveFromCart -> {
                removeFromCart(event.product)
            }

            is CatalogViewEvent.OnCartLoaded -> {
                reduce(event)
            }

            CatalogViewEvent.OnCartButtonClicked -> {
                callbacks.onNavigateToCart()
            }

            is CatalogViewEvent.OnProductCardClicked -> {
                callbacks.showProductCard(event.product.id.toInt())
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
                    print(it.message)
                }
                .collect {
                    print(it)
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
                    print(it.message)
                }
                .collect {
                    print(it)
                }
        }
    }

    private fun subscribeToCart() {
        coroutineScope.launch {
            cartRepository.cartSubject.collect {
                onEvent(CatalogViewEvent.OnCartLoaded(it))
            }
        }
    }
}
