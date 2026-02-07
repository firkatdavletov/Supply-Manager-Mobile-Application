package org.example.project.feature.catalog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.domain.models.ProductModel
import org.example.project.feature.home.ProductCard
import org.example.project.feature.ui_components.DefaultCartButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogContent(
    title: String,
    products: List<ProductModel>,
    cartPrice: Int,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = {},
    onAddToCart: (ProductModel) -> Unit = {},
    onRemoveFromCart: (ProductModel) -> Unit = {},
    onCartButtonClicked: () -> Unit = {}
) {
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    text = title
                )
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = products.size,
                    span = {
                        GridItemSpan(1)
                    }
                ) {
                    val product = products[it]
                    ProductCard(
                        modifier = Modifier
                            .height(216.dp)
                            .fillMaxWidth(),
                        imageUrl = product.imageUrl,
                        title = product.title,
                        count = product.count,
                        price = "${product.price.toInt()} ₽",
                        weight = "100 g",
                        onAddToCart = {
                            onAddToCart(product)
                        },
                        onRemoveFromCart = {
                            onRemoveFromCart(product)
                        }
                    )
                }
            }
            if (cartPrice > 0) {
                if (products.isNotEmpty()) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 16.dp),
                        onClick = onCartButtonClicked,
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = "$cartPrice ₽"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CatalogContent_Preview() {
    DeliveryAppTheme {
        CatalogContent(
            title = "Food",
            products = listOf(
                ProductModel(
                    id = 0,
                    title = "Ролл с креветкой",
                    description = "",
                    price = 10000,
                    imageUrl = null,
                    categoryId = 0,
                    count = 0,
                ),
                ProductModel(
                    id = 1,
                    title = "Ролл с креветкой",
                    description = "",
                    price = 10000,
                    imageUrl = null,
                    categoryId = 0,
                    count = 0,
                ),
                ProductModel(
                    id = 2,
                    title = "Ролл с креветкой",
                    description = "",
                    price = 10000,
                    imageUrl = null,
                    categoryId = 0,
                    count = 0,
                )
            ),
            cartPrice = 100
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CatalogContent_Preview_Night() {
    DeliveryAppTheme {
        CatalogContent(
            title = "Food",
            products = listOf(
                ProductModel(
                    id = 0,
                    title = "Ролл с креветкой",
                    description = "",
                    price = 10000,
                    imageUrl = null,
                    categoryId = 0,
                    count = 0,
                ),
                ProductModel(
                    id = 1,
                    title = "Ролл с креветкой",
                    description = "",
                    price = 10000,
                    imageUrl = null,
                    categoryId = 0,
                    count = 0,
                ),
                ProductModel(
                    id = 2,
                    title = "Ролл с креветкой",
                    description = "",
                    price = 10000,
                    imageUrl = null,
                    categoryId = 0,
                    count = 0,
                )
            ),
            cartPrice = 100
        )
    }
}