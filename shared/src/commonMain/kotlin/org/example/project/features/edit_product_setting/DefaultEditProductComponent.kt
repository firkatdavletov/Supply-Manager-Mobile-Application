package org.example.project.features.edit_product_setting

import com.arkivanov.decompose.ComponentContext
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.UnitOfMeasure
import org.example.project.features.SnackBarManager
import org.example.project.features.products_settings.ProductsSettingsStore

class DefaultEditProductComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: EditProductSettingCallbacks,
    private val productsStore: ProductsSettingsStore,
    productId: Long?,
) : EditProductComponent(
        componentContext = componentContext,
        initialState = buildInitialState(
            product = productId?.let(productsStore::getProductById),
            productId = productId,
        ),
        reducer = EditProductSettingReducer(),
        snackBarManager = snackBarManager,
    ) {

    override fun onEvent(event: EditProductSettingViewEvent) {
        when (event) {
            EditProductSettingViewEvent.OnBackClicked -> {
                callbacks.navigateBack()
            }

            is EditProductSettingViewEvent.OnNameChanged -> {
                reduce(event)
            }

            is EditProductSettingViewEvent.OnDescriptionChanged -> {
                reduce(event)
            }

            is EditProductSettingViewEvent.OnPriceChanged -> {
                reduce(event)
            }

            is EditProductSettingViewEvent.OnImageUrlChanged -> {
                reduce(event)
            }

            EditProductSettingViewEvent.OnSaveClicked -> {
                save()
            }

            EditProductSettingViewEvent.OnLoading -> {
                reduce(event)
            }

            EditProductSettingViewEvent.OnSaved -> {
                reduce(event)
            }

            is EditProductSettingViewEvent.OnError -> {
                reduce(event)
                showError(event.error)
            }

            is EditProductSettingViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
        }
    }

    private fun save() {
        val title = state.value.name.trim()
        if (title.isBlank()) {
            onEvent(EditProductSettingViewEvent.OnError("Введите название товара"))
            return
        }

        val priceValue = state.value.price
            .trim()
            .toLongOrNull()
        if (priceValue == null || priceValue < 0L) {
            onEvent(EditProductSettingViewEvent.OnError("Введите корректную цену"))
            return
        }

        onEvent(EditProductSettingViewEvent.OnLoading)

        val description = state.value.description
            .trim()
            .ifBlank { null }
        val imageUrl = state.value.imageUrl
            .trim()
            .ifBlank { null }
        val productId = state.value.productId
        val product = if (productId == null) {
            ProductModel(
                id = productsStore.nextProductId(),
                title = title,
                description = description,
                price = priceValue,
                imageUrl = imageUrl,
                categoryId = 0L,
                count = 0,
                countStep = 1,
                unit = UnitOfMeasure.PIECE,
            )
        } else {
            val oldProduct = productsStore.getProductById(productId)
            if (oldProduct != null) {
                oldProduct.copy(
                    title = title,
                    description = description,
                    price = priceValue,
                    imageUrl = imageUrl,
                )
            } else {
                ProductModel(
                    id = productId,
                    title = title,
                    description = description,
                    price = priceValue,
                    imageUrl = imageUrl,
                    categoryId = 0L,
                    count = 0,
                    countStep = 1,
                    unit = UnitOfMeasure.PIECE,
                )
            }
        }

        productsStore.upsertProduct(product)
        onEvent(EditProductSettingViewEvent.OnSaved)
        callbacks.navigateBack()
    }

    companion object {
        private fun buildInitialState(
            product: ProductModel?,
            productId: Long?,
        ): EditProductSettingViewState {
            return if (product == null) {
                EditProductSettingViewState(
                    title = "Новый товар",
                    productId = productId,
                    name = "",
                    description = "",
                    price = "",
                    imageUrl = "",
                )
            } else {
                EditProductSettingViewState(
                    title = "Редактирование товара",
                    productId = product.id,
                    name = product.title,
                    description = product.description.orEmpty(),
                    price = product.price.toString(),
                    imageUrl = product.imageUrl.orEmpty(),
                )
            }
        }
    }
}