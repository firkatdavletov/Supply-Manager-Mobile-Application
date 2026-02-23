package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.edit_product_setting.DefaultEditProductComponent
import org.example.project.features.edit_product_setting.EditProductComponent
import org.example.project.features.edit_product_setting.EditProductSettingCallbacks
import org.koin.dsl.module

fun editProductSettingModule() =
    module {
        factory<EditProductComponent> { (componentContext: ComponentContext, productId: Long?, callbacks: EditProductSettingCallbacks) ->
            DefaultEditProductComponent(
                componentContext = componentContext,
                snackBarManager = get(),
                callbacks = callbacks,
                productsStore = get(),
                productId = productId,
            )
        }
    }