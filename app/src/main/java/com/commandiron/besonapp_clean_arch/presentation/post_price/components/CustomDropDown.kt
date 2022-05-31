package com.commandiron.besonapp_clean_arch.presentation.post_price.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun CustomDropDown(
    onCategoryBoxClick:() -> Unit,
    title: String,
    isExpanded: Boolean,
    dropDownItems: List<String>?,
    onSelect:(Int?) -> Unit,
    onDismissRequest:(Int?) -> Unit
) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = Modifier.clickable { onCategoryBoxClick() },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Row(
            modifier = Modifier.padding(spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = if(isExpanded){
                    Icons.Default.KeyboardArrowDown
                }else Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        CustomDropDownMenu(
            modifier = Modifier.sizeIn(
                maxHeight = spacing.dropDownMenuHeight,
                maxWidth = spacing.spaceXXLarge
            ),
            isExpanded = isExpanded,
            dropDownItems = dropDownItems,
            onSelect = onSelect,
            onDismissRequest = onDismissRequest
        )
    }
}