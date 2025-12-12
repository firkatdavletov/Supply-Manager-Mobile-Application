package org.example.project.feature.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.DeliveryAppTheme
import org.example.project.R

@Composable
fun DefaultCartButton(
    modifier: Modifier = Modifier,
    text: String,
    count: Int = 0,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            modifier = Modifier
                .shadow(elevation = 10.dp, RoundedCornerShape(50))
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (count > 0) {
                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onRemoveClick()
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_minus_2_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleSmall,
                    text = count.toString()
                )
                Spacer(Modifier.weight(1f))
            }

            Image(
                modifier = Modifier
                    .size(16.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onAddClick()
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_plus_2_24),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFFC84D00
)
@Composable
private fun DefaultCartButton_Preview() {
    DeliveryAppTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                DefaultCartButton(
                    modifier = Modifier.width(100.dp),
                    text = "Add to cart",
                    onAddClick = {},
                    onRemoveClick = {},
                    count = 5
                )
                DefaultCartButton(
                    text = "Add to cart",
                    onAddClick = {},
                    onRemoveClick = {},
                    count = 5
                )
                DefaultCartButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Add to cart",
                    onAddClick = {},
                    onRemoveClick = {},
                    count = 0
                )
            }
        }
    }
}