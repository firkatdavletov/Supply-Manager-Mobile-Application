package org.example.project.di

import com.arkivanov.decompose.ComponentContext
import org.example.project.features.dialogs.deleteUserDialog.DefaultDeleteUserComponent
import org.example.project.features.dialogs.deleteUserDialog.DeleteUserComponent
import org.example.project.features.dialogs.deleteUserDialog.DeleteUserDialogCallbacks
import org.example.project.features.dialogs.logoutUserDialog.DefaultLogoutUserComponent
import org.example.project.features.dialogs.logoutUserDialog.LogoutUserComponent
import org.example.project.features.dialogs.logoutUserDialog.LogoutUserDialogCallbacks
import org.example.project.features.dialogs.productCard.DefaultProductCardComponent
import org.example.project.features.dialogs.productCard.ProductCardComponent
import org.example.project.navigation.DialogConfig
import org.koin.dsl.module

fun dialogsModule() =
    module {
        factory<ProductCardComponent> { (componentContext: ComponentContext, config: DialogConfig.ProductCard) ->
            DefaultProductCardComponent(
                componentContent = componentContext,
                productId = config.productId,
                snackBarManager = get(),
                getProductUseCase = get(),
                addToCartUseCase = get(),
                removeFromCartUseCase = get(),
                cartRepository = get(),
            )
        }

        factory<DeleteUserComponent> {
            (componentContext: ComponentContext, config: DialogConfig.DeleteUser, callbacks: DeleteUserDialogCallbacks),
            ->
            DefaultDeleteUserComponent(
                componentContext = componentContext,
                snackBarManager = get(),
                deleteUserUseCase = get(),
                callbacks = callbacks,
            )
        }

        factory<LogoutUserComponent> {
            (componentContext: ComponentContext, config: DialogConfig.LogoutUser, callbacks: LogoutUserDialogCallbacks),
            ->
            DefaultLogoutUserComponent(
                componentContext = componentContext,
                snackBarManager = get(),
                logoutUserUseCase = get(),
                callbacks = callbacks,
            )
        }
    }