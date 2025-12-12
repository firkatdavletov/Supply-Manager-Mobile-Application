package org.example.project.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.R
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ProductModel
import org.example.project.features.home.OrderUIModel

@Composable
fun HomeContent(
    modifier: Modifier,
    userName: String?,
    addressString: String,
    deliveryInfo: String,
    currentOrders: List<OrderUIModel>,
    categories: List<CategoryModel> = emptyList(),
    totalAmount: Float = 0f,
    onChangeAddressClicked: () -> Unit = {},
    onCategoryClicked: (CategoryModel) -> Unit = {},
    onAddToCart: (ProductModel) -> Unit = {},
    onRemoveFromCart: (ProductModel) -> Unit = {},
    onCartButtonClicked: () -> Unit = {},
    onPersonClicked: () -> Unit = {},
    onOrderClicked: (Long) -> Unit = {},
) {
    val lazyGridState = rememberLazyGridState()
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val focusRequester = remember { FocusRequester() }

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            currentOrders.size
        }
    )

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusRequester.freeFocus()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onChangeAddressClicked()
                        },
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.titleMedium,
                            text = addressString,
                        )
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(1.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(4.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            text = "Изменить",
                        )
                    }
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        text = deliveryInfo,
                    )
                }
                IconButton(
                    onClick = {
                        onPersonClicked()
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_person_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                state = lazyGridState
            ) {
                if (userName != null) {
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                text = "Привет, $userName!"
                            )
                        }
                    }
                }
                item(
                    span = {
                        GridItemSpan(3)
                    }
                ) {
                    HorizontalPager(
                        state = pagerState,
                        pageSize = PageSize.Fill,
                        pageSpacing = 16.dp,
                    ) {
                        val order = currentOrders[it]

                        HomeOrderView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onOrderClicked(order.id)
                                },
                            orderNumber = order.number,
                            status = order.status,
                            amount = order.amount
                        )
                    }
                }
//                item(
//                    span = {
//                        GridItemSpan(3)
//                    }
//                ) {
//                    OutlinedTextField(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(40.dp)
//                            .focusRequester(focusRequester),
//                        contentPadding = PaddingValues(vertical = 8.dp),
//                        state = textState,
//                        shape = RoundedCornerShape(16.dp),
//                        textStyle = MaterialTheme.typography.bodyLarge,
//                        placeholder = {
//                            Text(
//                                style = MaterialTheme.typography.bodyLarge,
//                                text = "Поиск"
//                            )
//                        },
//                        leadingIcon = {
//                            Icon(
//                                imageVector = ImageVector.vectorResource(R.drawable.ic_loupe_16),
//                                contentDescription = null,
//                            )
//                        }
//                    )
//                }
                items(
                    count = categories.size,
                    key = {
                        categories[it].id
                    },
                    span = {
                        GridItemSpan(categories[it].span)
                    }
                ) {
                    HomeCategoryView(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onCategoryClicked(categories[it])
                            },
                        title = categories[it].title,
                        image = categories[it].imageUrl,
                    )
                }
                for (category in categories) {
                    item(span = {
                        GridItemSpan(3)
                    }) {
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            text = category.title
                        )
                    }
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                count = category.products.size
                            ) {
                                val product = category.products[it]
                                ProductCard(
                                    modifier = Modifier
                                        .height(166.dp)
                                        .width(166.dp),
                                    imageUrl = product.imageUrl,
                                    title = product.title,
                                    price = "${product.price.toInt()} ₽",
                                    weight = "100g",
                                    count = product.count,
                                    onAddToCart = {
                                        onAddToCart(product)
                                    },
                                    onRemoveFromCart = {
                                        onRemoveFromCart(product)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            if (totalAmount > 0) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp),
                    onClick = onCartButtonClicked
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = "${totalAmount.toInt()} ₽"
                    )
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun HomeContent_Preview() {
    DeliveryAppTheme(
        dynamicColor = false
    ) {
        val dummy = object : SnackbarData {
            override val visuals = object : SnackbarVisuals {
                override val message: String  = "TEst"
                override val actionLabel: String = "Test"
                override val withDismissAction: Boolean = false
                override val duration: SnackbarDuration = SnackbarDuration.Long

            }

            override fun dismiss() {}
            override fun performAction() {}
        }
        val snackbarHostState = SnackbarHostState()
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    modifier = Modifier.padding(8.dp),
                    hostState = snackbarHostState,
                    snackbar = {
                        Snackbar(
                            snackbarData = dummy
                        )
                    }
                )
            },
            contentWindowInsets = WindowInsets(0,0,0,0)
        ) { scaffoldPadding ->
            HomeContent(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
//                .background(brush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.surfaceVariant)))
                    .background(MaterialTheme.colorScheme.background)
                    .systemBarsPadding(),
                userName = null,
                addressString = "улица Косоротова, 6",
                deliveryInfo = "Доставка 100 ₽",
                currentOrders = listOf(
                    OrderUIModel(
                        number = "1243",
                        status = "Собираем",
                        amount = 100,
                        id = 1
                    )
                ),
                totalAmount = 100f,
                categories = listOf(
                    CategoryModel(
                        id = 0,
                        title = "Выбор пользователей",
                        imageUrl = null,
                        parentCategoryId = 0,
                        selected = true,
                        products = listOf(
                            ProductModel(
                                id = 0,
                                title = "Ролл с креветкой",
                                description = "",
                                price = 100f,
                                imageUrl = null,
                                categoryId = 0,
                                count = 1,
                            ),
                            ProductModel(
                                id = 1,
                                title = "Ролл с креветкой",
                                description = "",
                                price = 100f,
                                imageUrl = null,
                                categoryId = 0,
                                count = 0,
                            ),
                            ProductModel(
                                id = 2,
                                title = "Ролл с креветкой",
                                description = "",
                                price = 100f,
                                imageUrl = null,
                                categoryId = 0,
                                count = 2,
                            )
                        ),
                        span = 1,
                    ),
                    CategoryModel(
                        id = 1,
                        title = "Новинки",
                        imageUrl = null,
                        parentCategoryId = 0,
                        selected = false,
                        products = listOf(
                            ProductModel(
                                id = 0,
                                title = "Ролл с креветкой",
                                description = "",
                                price = 100f,
                                imageUrl = null,
                                categoryId = 0,
                                count = 0,
                            ),
                            ProductModel(
                                id = 1,
                                title = "Ролл с креветкой",
                                description = "",
                                price = 100f,
                                imageUrl = null,
                                categoryId = 0,
                                count = 0,
                            ),
                            ProductModel(
                                id = 2,
                                title = "Ролл с креветкой",
                                description = "",
                                price = 100f,
                                imageUrl = null,
                                categoryId = 0,
                                count = 0,
                            )
                        ),
                        span = 1,
                    )
                )
            )
        }

    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun HomeContent_Preview_Night() {
    DeliveryAppTheme(
        dynamicColor = false
    ) {
        HomeContent(
            modifier = Modifier
                .fillMaxSize()
//                .background(brush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.surfaceVariant)))
                .background(MaterialTheme.colorScheme.background)
                .systemBarsPadding(),
            userName = "Фиркат",
            addressString = "улица Косоротова, 6",
            deliveryInfo = "Доставка 100 ₽",
            currentOrders = listOf(
                OrderUIModel(
                    number = "1243",
                    status = "Собираем",
                    amount = 100,
                    id = 1
                )
            ),
            totalAmount = 100f,
            categories = listOf(
                CategoryModel(
                    id = 0,
                    title = "Выбор пользователей",
                    imageUrl = null,
                    parentCategoryId = 0,
                    selected = true,
                    products = listOf(
                        ProductModel(
                            id = 0,
                            title = "Ролл с креветкой",
                            description = "",
                            price = 100f,
                            imageUrl = null,
                            categoryId = 0,
                            count = 1,
                        ),
                        ProductModel(
                            id = 1,
                            title = "Ролл с креветкой",
                            description = "",
                            price = 100f,
                            imageUrl = null,
                            categoryId = 0,
                            count = 0,
                        ),
                        ProductModel(
                            id = 2,
                            title = "Ролл с креветкой",
                            description = "",
                            price = 100f,
                            imageUrl = null,
                            categoryId = 0,
                            count = 2,
                        )
                    ),
                    span = 1,
                ),
                CategoryModel(
                    id = 1,
                    title = "Новинки",
                    imageUrl = null,
                    parentCategoryId = 0,
                    selected = false,
                    products = listOf(
                        ProductModel(
                            id = 0,
                            title = "Ролл с креветкой",
                            description = "",
                            price = 100f,
                            imageUrl = null,
                            categoryId = 0,
                            count = 0,
                        ),
                        ProductModel(
                            id = 1,
                            title = "Ролл с креветкой",
                            description = "",
                            price = 100f,
                            imageUrl = null,
                            categoryId = 0,
                            count = 0,
                        ),
                        ProductModel(
                            id = 2,
                            title = "Ролл с креветкой",
                            description = "",
                            price = 100f,
                            imageUrl = null,
                            categoryId = 0,
                            count = 0,
                        )
                    ),
                    span = 1,
                )
            )
        )
    }
}