package com.commandiron.besonapp_clean_arch.presentation.prices.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.MyGray
import com.commandiron.besonapp_clean_arch.ui.theme.MyGrayDarker

@Composable
fun HorizontalCategorySelector(
    itemList: List<SubConstructionItem>,
    onItemClick:(Int?) -> Unit
) {
    val spacing = LocalSpacing.current
    var selectedItemTitle by remember { mutableStateOf("") }
    LazyRow(
        content = {
            items(itemList){ item ->
                var isSelected by remember { mutableStateOf(false) }
                isSelected = item.title == selectedItemTitle
                Row(
                    modifier = Modifier
                        .widthIn(min = spacing.spaceExtraLarge, max = spacing.spaceXXXLarge)
                        .border(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary else MyGray,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = spacing.spaceSmall)
                        .clickable {
                            if(isSelected){
                                selectedItemTitle = ""
                                onItemClick(null)
                            }else{
                                selectedItemTitle = item.title
                                onItemClick(item.id)
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(isSelected){
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(10.dp),
                        color = if(isSelected) MaterialTheme.colorScheme.primary else MyGrayDarker,
                        maxLines = 1
                    )
                }
            }
        },
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
    )
}