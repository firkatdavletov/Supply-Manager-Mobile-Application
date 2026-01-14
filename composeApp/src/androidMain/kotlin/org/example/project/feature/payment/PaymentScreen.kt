package org.example.project.feature.payment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.payment.PaymentComponent
import org.example.project.features.payment.PaymentViewEvent

@Composable
fun PaymentScreen(component: PaymentComponent) {
    val state by component.state.subscribeAsState()

    BackHandler {
        component.onEvent(PaymentViewEvent.OnBackButtonClicked)
    }

    PaymentContent(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        deliveryType = state.deliveryType,
        addressString = state.addressString,
        departmentName = state.departmentName,
        isPrivateHome = state.isPrivateHome,
        entrance = state.entrance,
        entranceInputError = state.entranceInputError,
        flat = state.flat,
        flatInputError = state.flatInputError,
        comment = state.comment,
        totalAmount = state.totalAmount,
        deliveryPrice = state.deliveryPrice,
        productPrice = state.productPrice,
        paymentTypes = state.paymentTypes,
        onBackButtonClicked = {
            component.onEvent(PaymentViewEvent.OnBackButtonClicked)
        },
        onConfirmClicked = {
            component.onEvent(PaymentViewEvent.OnConfirmButtonClicked)
        },
        onChangeDeliveryType = {
            component.onEvent(PaymentViewEvent.OnChangeDeliveryType(it))
        },
        onSelectAddress = {
            component.onEvent(PaymentViewEvent.OnChangeAddress)
        },
        onIsPrivateHouseChanged = {
            component.onEvent(PaymentViewEvent.OnIsPrivateHouseChanged(it))
        },
        onFlatChanged = {
            component.onEvent(PaymentViewEvent.OnFlatChanged(it))
        },
        onEntranceChanged = {
            component.onEvent(PaymentViewEvent.OnEntranceChanged(it))
        },
        onCommentChanged = {
            component.onEvent(PaymentViewEvent.OnCommentChanged(it))
        }
    )
}