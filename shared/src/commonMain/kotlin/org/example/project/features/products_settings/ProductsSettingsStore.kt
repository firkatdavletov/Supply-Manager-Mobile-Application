package org.example.project.features.products_settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.domain.models.ProductModel

class ProductsSettingsStore {
    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> = _products.asStateFlow()

    fun setProducts(products: List<ProductModel>) {
        _products.value = products.distinctBy { it.id }
    }

    fun upsertProduct(product: ProductModel) {
        val current = _products.value.toMutableList()
        val existingIndex = current.indexOfFirst { it.id == product.id }

        if (existingIndex == -1) {
            current.add(product)
        } else {
            current[existingIndex] = product
        }

        _products.value = current
    }

    fun getProductById(id: Long): ProductModel? {
        return _products.value.firstOrNull { it.id == id }
    }

    fun nextProductId(): Long {
        return (_products.value.maxOfOrNull { it.id } ?: 0L) + 1L
    }
}
