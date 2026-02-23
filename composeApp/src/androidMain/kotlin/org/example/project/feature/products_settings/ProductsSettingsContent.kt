package org.example.project.feature.products_settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.domain.models.ProductModel
import org.example.project.domain.models.UnitOfMeasure

@Composable
fun ProductsSettingsContent(
    title: String,
    products: List<ProductModel>,
    searchQuery: String,
    selectedProductId: Long?,
    isLoading: Boolean,
    onBackClicked: () -> Unit = {},
    onAddProductClicked: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onProductClicked: (Long) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
                    onClick = onBackClicked,
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
                    text = title,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onAddProductClicked,
                ) {
                    Text(text = "Добавить товар")
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    singleLine = true,
                    label = { Text("Поиск товаров") },
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (products.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            text = "Товары не найдены",
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp),
                            ),
                    ) {
                        items(
                            count = products.size,
                        ) { index ->
                            val product = products[index]
                            val isSelected = product.id == selectedProductId
                            val rowColor =
                                if (isSelected) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(rowColor)
                                    .clickable { onProductClicked(product.id) }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                ) {
                                    Text(
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        text = product.title,
                                    )
                                    Text(
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        text = "Цена: ${product.price}",
                                    )
                                    if (!product.description.isNullOrBlank()) {
                                        Text(
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            text = product.description.orEmpty(),
                                        )
                                    }
                                }
                                Icon(
                                    modifier = Modifier.rotate(180f),
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_16),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }

                            if (index < products.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(start = 16.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProductsSettingsContentPreview() {
    DeliveryAppTheme {
        ProductsSettingsContent(
            title = "Карточки товаров",
            products = listOf(
                ProductModel(
                    id = 1,
                    title = "Пицца Маргарита",
                    description = "Томаты, сыр",
                    price = 129900,
                    imageUrl = null,
                    categoryId = 1,
                    count = 0,
                    countStep = 1,
                    unit = UnitOfMeasure.PIECE,
                ),
                ProductModel(
                    id = 2,
                    title = "Тирамису",
                    description = null,
                    price = 39900,
                    imageUrl = null,
                    categoryId = 2,
                    count = 0,
                    countStep = 1,
                    unit = UnitOfMeasure.PIECE,
                ),
            ),
            searchQuery = "",
            selectedProductId = 1,
            isLoading = false,
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProductsSettingsContentNightPreview() {
    DeliveryAppTheme {
        ProductsSettingsContent(
            title = "Карточки товаров",
            products = emptyList(),
            searchQuery = "Пиц",
            selectedProductId = null,
            isLoading = false,
        )
    }
}
