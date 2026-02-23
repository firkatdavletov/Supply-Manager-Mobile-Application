package org.example.project.features.products_settings

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.catalog.GetAllProductsUseCase
import org.example.project.features.SnackBarManager

class DefaultProductsSettingsComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: ProductsSettingsCallbacks,
    private val productsStore: ProductsSettingsStore,
    private val getAllProductsUseCase: GetAllProductsUseCase,
) : ProductsSettingsComponent(
        componentContext = componentContext,
        initialState = ProductsSettingsViewState(),
        reducer = ProductsSettingsReducer(),
        snackBarManager = snackBarManager,
    ) {
    private var subscribeJob: Job? = null

    override fun onStart() {
        subscribeToProducts()
        if (productsStore.products.value.isEmpty()) {
            loadProducts()
        }
    }

    override fun onStop() {
        subscribeJob?.cancel()
        subscribeJob = null
    }

    override fun onEvent(event: ProductsSettingsViewEvent) {
        when (event) {
            ProductsSettingsViewEvent.OnBackClicked -> {
                callbacks.navigateBack()
            }

            ProductsSettingsViewEvent.OnAddProductClicked -> {
                callbacks.navigateToAddProduct()
            }

            is ProductsSettingsViewEvent.OnSearchQueryChanged -> {
                reduce(event)
            }

            is ProductsSettingsViewEvent.OnProductClicked -> {
                reduce(event)
                callbacks.navigateToEditProduct(event.productId)
            }

            is ProductsSettingsViewEvent.OnProductsLoaded -> {
                reduce(event)
            }

            ProductsSettingsViewEvent.OnLoading -> {
                reduce(event)
            }

            is ProductsSettingsViewEvent.OnError -> {
                reduce(event)
                showError(event.error)
            }

            is ProductsSettingsViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
        }
    }

    private fun subscribeToProducts() {
        subscribeJob?.cancel()
        subscribeJob = coroutineScope.launch {
            productsStore.products.collect { products ->
                onEvent(ProductsSettingsViewEvent.OnProductsLoaded(products))
            }
        }
    }

    private fun loadProducts() {
        coroutineScope.launch {
            onEvent(ProductsSettingsViewEvent.OnLoading)
            getAllProductsUseCase
                .invoke(Unit)
                .catch {
                    onEvent(ProductsSettingsViewEvent.OnThrowError(it))
                }.collect { products ->
                    productsStore.setProducts(products)
                }
        }
    }
}
