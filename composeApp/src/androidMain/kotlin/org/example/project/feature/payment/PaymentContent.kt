package org.example.project.feature.payment

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.domain.models.DeliveryType
import org.example.project.domain.models.PaymentTypeModel
import org.example.project.feature.ui_components.SelectedButton

@Composable
fun PaymentContent(
    modifier: Modifier,
    deliveryType: DeliveryType,
    addressString: String?,
    departmentName: String?,
    isPrivateHome: Boolean,
    entrance: String,
    entranceInputError: String? = null,
    flat: String,
    flatInputError: String? = null,
    comment: String,
    totalAmount: Long,
    deliveryPrice: Long,
    productPrice: Long,
    paymentTypes: List<PaymentTypeModel>,
    onChangeDeliveryType: (DeliveryType) -> Unit = {},
    onBackButtonClicked: () -> Unit = {},
    onSelectAddress: () -> Unit = {},
    onConfirmClicked: () -> Unit = {},
    onIsPrivateHouseChanged: (Boolean) -> Unit = {},
    onEntranceChanged: (String) -> Unit = {},
    onFlatChanged: (String) -> Unit = {},
    onCommentChanged: (String) -> Unit = {},
) {

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp),
            ) {
                IconButton(
                    onClick = onBackButtonClicked,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_16),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = "Оформление заказа"
                )
            }

            if (deliveryType == DeliveryType.DELIVERY) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Куда"
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (addressString != null) {
                            Text(
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.titleMedium,
                                text = addressString
                            )
                        }
                        SelectedButton(
                            title = if (addressString == null) "Выбрать адрес" else "Изменить адрес",
                            selected = false,
                            onClick = onSelectAddress
                        )
                    }

                    if (addressString != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            SelectedButton(
                                title = "Частный дом",
                                selected = isPrivateHome,
                                onClick = {
                                    if (!isPrivateHome) {
                                        onIsPrivateHouseChanged(true)
                                    }
                                }
                            )
                            SelectedButton(
                                title = "Квартира/офис",
                                selected = !isPrivateHome,
                                onClick = {
                                    if (isPrivateHome) {
                                        onIsPrivateHouseChanged(false)
                                    }
                                }
                            )
                        }

                        if (!isPrivateHome) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(64.dp),
                                    value = entrance,
                                    onValueChange = onEntranceChanged,
                                    shape = RoundedCornerShape(50),
                                    textStyle = MaterialTheme.typography.bodyLarge,
                                    label = {
                                        Text(
                                            style = MaterialTheme.typography.bodyLarge,
                                            text = "Подъезд"
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Decimal
                                    ),
                                    isError = entranceInputError != null
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(64.dp),
                                    value = flat,
                                    onValueChange = onFlatChanged,
                                    shape = RoundedCornerShape(50),
                                    textStyle = MaterialTheme.typography.bodyLarge,
                                    label = {
                                        Text(
                                            style = MaterialTheme.typography.bodyLarge,
                                            text = "Квартира/офис"
                                        )
                                    },
                                    isError = flatInputError != null
                                )
                            }
                        }
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .height(64.dp),
                            value = comment,
                            onValueChange = onCommentChanged,
                            shape = RoundedCornerShape(50),
                            textStyle = MaterialTheme.typography.bodyLarge,
                            label = {
                                Text(
                                    style = MaterialTheme.typography.bodyLarge,
                                    text = "Комментарий"
                                )
                            },
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Откуда"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (departmentName != null) {
                            Text(
                                style = MaterialTheme.typography.titleMedium,
                                text = departmentName
                            )
                        }
                    }
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(64.dp),
                    value = comment,
                    onValueChange = onCommentChanged,
                    shape = RoundedCornerShape(50),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    label = {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = "Комментарий"
                        )
                    },
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Оплата"
                )

                Spacer(Modifier.height(16.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(count = paymentTypes.size) {
                        val paymentType = paymentTypes[it]
                        val selected = paymentType.selected
                        SelectedButton(
                            title = paymentType.title,
                            selected = selected,
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Стоимость продуктов:"
                )
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "$productPrice ₽"
                )
            }

            if (deliveryType == DeliveryType.DELIVERY) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Стоимость доставки:"
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        text = if (deliveryPrice == 0L) "бесплатно" else "$deliveryPrice ₽"
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Итого:"
                )
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "$totalAmount ₽"
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = onConfirmClicked,
                enabled = when (deliveryType) {
                    DeliveryType.DELIVERY -> addressString != null
                    DeliveryType.PICKUP -> true
                }
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Заказать"
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PaymentContentDelivery_Preview() {
    DeliveryAppTheme {
        PaymentContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            deliveryType = DeliveryType.DELIVERY,
            addressString = null,
            departmentName = "ул. Щербакова, 150/2",
            entrance = "2",
            flat = "88",
            comment = "",
            totalAmount = 500,
            productPrice = 400,
            deliveryPrice = 100,
            isPrivateHome = false,
            paymentTypes = listOf(
                PaymentTypeModel(
                    id = "cash",
                    title = "Картой курьеру",
                    selected = true
                ),
                PaymentTypeModel(
                    id = "cash",
                    title = "Наличными курьеру",
                    selected = false
                )
            ),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PaymentContentDelivery_Preview_IsPrivateHouse() {
    DeliveryAppTheme {
        PaymentContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            deliveryType = DeliveryType.DELIVERY,
            addressString = "ул. Щербакова, 150/2",
            departmentName = "ул. Щербакова, 150/2",
            entrance = "2",
            flat = "88",
            comment = "",
            totalAmount = 500,
            productPrice = 400,
            deliveryPrice = 100,
            isPrivateHome = true,
            paymentTypes = listOf(
                PaymentTypeModel(
                    id = "cash",
                    title = "Картой курьеру",
                    selected = true
                ),
                PaymentTypeModel(
                    id = "cash",
                    title = "Наличными курьеру",
                    selected = false
                )
            ),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PaymentContentPickup_Preview() {
    DeliveryAppTheme {
        PaymentContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            deliveryType = DeliveryType.PICKUP,
            addressString = "ул. Щербакова, 150/2",
            departmentName = "ул. Щербакова, 150/2",
            entrance = "2",
            flat = "88",
            comment = "",
            totalAmount = 500,
            productPrice = 400,
            deliveryPrice = 100,
            isPrivateHome = false,
            paymentTypes = listOf(
                PaymentTypeModel(
                    id = "cash",
                    title = "Картой при получении",
                    selected = false
                ),
                PaymentTypeModel(
                    id = "cash",
                    title = "Наличными при получении",
                    selected = true
                )
            ),
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PaymentContentPickup_Preview_Night() {
    DeliveryAppTheme {
        PaymentContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            deliveryType = DeliveryType.DELIVERY,
            addressString = "ул. Щербакова, 150/2",
            departmentName = "ул. Щербакова, 150/2",
            entrance = "2",
            flat = "88",
            comment = "",
            totalAmount = 500,
            productPrice = 400,
            deliveryPrice = 100,
            isPrivateHome = false,
            paymentTypes = listOf(
                PaymentTypeModel(
                    id = "cash",
                    title = "Картой при получении",
                    selected = false
                ),
                PaymentTypeModel(
                    id = "cash",
                    title = "Наличными при получении",
                    selected = true
                )
            ),
        )
    }
}