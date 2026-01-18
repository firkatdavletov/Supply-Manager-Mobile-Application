package org.example.project.features.home

import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.DepartmentModel
import org.example.project.features.base.Reducer

data class HomeViewState(
    val userName: String?,
    val categories: List<CategoryModel>,
    val currentOrders: List<OrderUIModel>,
    val freeDeliveryPrice: Double?,
    val productsPrice: Double,
    val amount: Double,
    val deliveryType: DeliveryType,
    val deliveryInfo: String,
    val deliveryAddress: String,
    val cartDepartment: DepartmentModel?,
    val storeIsClosed: Boolean,
): Reducer.ViewState