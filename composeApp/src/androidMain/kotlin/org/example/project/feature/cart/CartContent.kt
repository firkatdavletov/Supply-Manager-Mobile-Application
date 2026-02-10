package org.example.project.feature.cart

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import com.example.ui.theme.AppTypography
import org.example.project.R
import org.example.project.domain.models.CartItemModel
import org.example.project.domain.models.DeliveryType
import org.example.project.feature.ui_components.DefaultCartButton

@Composable
fun CartContent(
    modifier: Modifier,
    products: List<CartItemModel>,
    productPrice: Long,
    deliveryPrice: Long,
    totalAmount: Long,
    deliveryType: DeliveryType,
    onBackButtonClicked: () -> Unit = {},
    onAddToCart: (CartItemModel) -> Unit = {},
    onRemoveFromCart: (CartItemModel) -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    Box(
        modifier = modifier,
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
                    text = "Корзина"
                )
            }

            if (products.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    items(
                        items = products
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    style = AppTypography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    text = it.title
                                )
                            }
                            DefaultCartButton(
                                modifier = Modifier
                                    .width(116.dp),
                                text = "",
                                count = it.quantity,
                                onAddClick = {
                                    onAddToCart(it)
                                },
                                onRemoveClick = {
                                    onRemoveFromCart(it)
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.weight(1f))
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
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "В корзине пусто"
                    )
                }
            }
            if (products.isNotEmpty()) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp),
                    onClick = onConfirm
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = "Перейти к оплате"
                    )
                }
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp),
                    onClick = onBackButtonClicked,
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = "В меню"
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CartContentEmpty_Preview() {
    DeliveryAppTheme {
        CartContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            deliveryType = DeliveryType.DELIVERY,
            products = emptyList(),
            productPrice = 600,
            deliveryPrice = 100,
            totalAmount = 700
        )
    }
}