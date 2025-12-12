package org.example.project.feature.ui_components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInputField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    value: String,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    onValueChange: (String) -> Unit,
    errorText: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = FocusRequester(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    ) {
    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .height(40.dp)
            .fillMaxWidth(),
        value = value,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 16.dp),
                placeholder = {
                    Text(
                        text = placeholder,
                    )
                },
                container = {
                    OutlinedTextFieldDefaults.ContainerBox(
                        enabled = enabled,
                        isError = errorText != null,
                        interactionSource = interactionSource,
                        shape = RoundedCornerShape(50),
                        unfocusedBorderThickness = 1.dp,
                        focusedBorderThickness = 1.dp,
                        colors = OutlinedTextFieldDefaults.colors()
                    )
                },
                leadingIcon = if (leadingIcon != null) {
                    {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            imageVector = leadingIcon,
                            contentDescription = null
                        )
                    }
                } else null,
                trailingIcon = if (value.isNotEmpty() && enabled) {
                    {
                        IconButton(onClick = { onValueChange("") }) {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp),
                                imageVector = ImageVector.vectorResource(
                                    id = org.example.project.R.drawable.ic_cross_circle_fill_m
                                ),
                                contentDescription = null
                            )
                        }
                    }
                } else null,
            )
        },

    )
}