package com.commandiron.besonapp_clean_arch.presentation.post_price.components

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.NoErrorGreen

@Composable
fun CustomDropDown(
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Unspecified,
    onCategoryBoxClick:() -> Unit,
    title: String,
    isExpanded: Boolean,
    offset: DpOffset,
    dropDownItems: List<String>?,
    onSelect:(Int?) -> Unit,
    onDismissRequest:(Int?) -> Unit
) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onCategoryBoxClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = if(isExpanded){
                    Icons.Default.KeyboardArrowDown
                }else Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
        CustomDropDownMenu(
            modifier = Modifier.sizeIn(
                maxHeight = spacing.dropDownMenuHeight,
                maxWidth = spacing.spaceXXLarge
            ),
            borderColor = borderColor,
            isExpanded = isExpanded,
            offset = offset,
            dropDownItems = dropDownItems,
            onSelect = onSelect,
            onDismissRequest = onDismissRequest
        )
    }
}