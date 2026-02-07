package org.example.project.features.catalog

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.repositories.CartRepository
import org.example.project.domain.usecase.cart.AddToCartUseCase
import org.example.project.domain.usecase.cart.RemoveFromCartUseCase
import org.example.project.domain.usecase.catalog.GetCategoryUseCase
import org.example.project.domain.usecase.catalog.GetRemoteCategoriesUseCase

class DefaultCatalogComponent(
    componentContext: ComponentContext,
    private val categoryId: Int?,
    private val callbacks: CatalogCallbacks,
    private val getCategoriesUseCase: GetRemoteCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val cartRepository: CartRepository,
): CatalogComponent (
    componentContext = componentContext,
    initialState = CatalogViewState(),
    reducer = CatalogReducer(),
) {
    private var job: Job? = null
    private var subscribeJob: Job? = null

    override fun onStart() {
        if (categoryId == null) {
            getCategories()
        } else {
            subscribeToCart()
            getCategory(categoryId.toLong())
        }
    }

    override fun onStop() {
        subscribeJob?.cancel()
        job?.cancel()
        subscribeJob = null
        job = null
    }

    override fun onEvent(event: CatalogViewEvent) {
        when (event) {
            CatalogViewEvent.OnBackClicked -> {
                callbacks.onBack()
            }

            is CatalogViewEvent.OnCategoryClicked -> {
                callbacks.onNavigateToCategory(event.categoryId.toInt())
            }

            is CatalogViewEvent.OnCategoriesLoaded -> {
                reduce(event)
            }

            is CatalogViewEvent.OnCategoryLoaded -> {
                reduce(event)
            }

            is CatalogViewEvent.OnCartLoaded -> {
                reduce(event)
            }

            CatalogViewEvent.OnCartButtonClicked -> {
                callbacks.onNavigateToCart()
            }

            is CatalogViewEvent.OnAddToCart -> {
                addToCart(event.product)
            }

            is CatalogViewEvent.OnRemoveFromCart -> {
                removeFromCart(event.product)
            }

            is CatalogViewEvent.OnProductClicked -> {
                callbacks.showProductCard(event.id.toInt())
            }
        }
    }

    private fun subscribeToCart() {
        subscribeJob?.cancel()
        subscribeJob = coroutineScope.launch {
            cartRepository.cartSubject.collect {
                onEvent(CatalogViewEvent.OnCartLoaded(it))
            }
        }
    }

    private fun getCategories() {
        coroutineScope.launch {
            getCategoriesUseCase.invoke(Unit)
                .catch {  }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {}
                        ResultModel.Loading -> {}
                        is ResultModel.Success<List<CategoryModel>> -> {
                            onEvent(
                                CatalogViewEvent.OnCategoriesLoaded(
                                    resultModel.data
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun getCategory(id: Long) {
        coroutineScope.launch {
            getCategoryUseCase.invoke(id)
                .catch {  }
                .collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {}
                        ResultModel.Loading -> {}
                        is ResultModel.Success<CategoryModel> -> {
                            onEvent(
                                CatalogViewEvent.OnCategoryLoaded(
                                    resultModel.data
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun addToCart(product: ProductModel) {
        job?.cancel()
        job = coroutineScope.launch {
            val params = AddToCartUseCase.Params(product)
            addToCartUseCase.invoke(params)
                .catch {  }
                .collect {

                }
        }
    }

    private fun removeFromCart(product: ProductModel) {
        job?.cancel()
        job = coroutineScope.launch {
            val params = RemoveFromCartUseCase.Params(product)
            removeFromCartUseCase.invoke(params)
                .catch {  }
                .collect {

                }
        }
    }
}
