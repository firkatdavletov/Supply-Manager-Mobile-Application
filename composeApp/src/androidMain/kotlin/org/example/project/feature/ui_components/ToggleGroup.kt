package org.example.project.feature.ui_components

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ToggleGroup(
    items: List<String>,
    height: Dp,
    backgroundColor: Color,
    selectedColor: Color,
    contentSelectedColor: Color,
    contentUnselectedColor: Color,
    textStyle: TextStyle,
    innerPadding: Dp,
    borderStroke: BorderStroke? = null,
    modifier: Modifier = Modifier,
    selectedItem: String? = null,
    onClick: (String) -> Unit,
) {
    val context = LocalContext.current
    var containerWidth by remember { mutableFloatStateOf(0f) }
    val selectedIndex = items.indexOf(selectedItem).takeIf { it >= 0 } ?: 0
    val itemWidth = if (items.isNotEmpty()) containerWidth / items.size.toFloat() else 0f
    val offset by animateFloatAsState(
        targetValue = selectedIndex * itemWidth,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(backgroundColor, RoundedCornerShape(50))
            .conditional(borderStroke != null) {
                border(
                    width = borderStroke!!.width,
                    brush = borderStroke.brush,
                    shape = RoundedCornerShape(50)
                )
            }
            .padding(innerPadding)
            .onSizeChanged { containerWidth = it.width.pixelsToDp(context) }
    ) {
        // Анимированный индикатор выбора
        Box(
            modifier = Modifier
                .offset(x = offset.dp)
                .width(itemWidth.dp)
                .fillMaxHeight()
                .background(selectedColor, RoundedCornerShape(50))
        )

        Row(modifier = Modifier.fillMaxSize()) {
            items.forEach { value ->
                ToggleTextItem(
                    text = value,
                    contentSelectedColor = contentSelectedColor,
                    contentUnselectedColor = contentUnselectedColor,
                    textStyle = textStyle,
                    selected = value == selectedItem,
                    modifier = Modifier.weight(1f),
                    onClick = { onClick(value) }
                )
            }
        }
    }
}

fun Int.pixelsToDp(context: Context): Float {
    val densityDpi = context.resources.displayMetrics.densityDpi
    return this / (densityDpi / 160f)
}