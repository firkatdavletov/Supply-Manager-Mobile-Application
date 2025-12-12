package org.example.project.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.compose.DeliveryAppTheme
import org.example.project.feature.ui_components.DefaultCartButton

@Composable
fun ProductCard(
    modifier: Modifier,
    imageUrl: String?,
    title: String,
    price: String,
    weight: String,
    count: Int,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit,
) {
    Column {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            DefaultCartButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
                text = "Добавить",
                count = count,
                onAddClick = onAddToCart,
                onRemoveClick = onRemoveFromCart
            )
        }
        Text(
            text = price,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier,
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun ProductCard_Preview() {
    DeliveryAppTheme {
        ProductCard(
            modifier = Modifier
                .height(186.dp)
                .width(166.dp),
            imageUrl = "",
            title = "Горячий ролл с креветкой",
            price = "1000 ₽",
            weight = "100 г",
            count = 1,
            onAddToCart = {},
            onRemoveFromCart = {}
        )
    }
}