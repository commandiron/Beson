package com.commandiron.besonapp_clean_arch.presentation.post_price.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.NoErrorGreen
import com.commandiron.besonapp_clean_arch.ui.theme.SignUpCompanyBackgroundColor
import com.commandiron.besonapp_clean_arch.ui.theme.SignUpCustomerBackgroundColor

@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    borderColor: Color,
    isExpanded: Boolean,
    offset: DpOffset,
    dropDownItems: List<String>?,
    onSelect:(Int) -> Unit,
    onDismissRequest:(Int?) -> Unit,
) {
    val spacing = LocalSpacing.current
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    DropdownMenu(
        modifier = modifier.background(Color.White),
        expanded = isExpanded,
        offset = offset,
        onDismissRequest = {
            onDismissRequest(selectedIndex)
        },
    ) {
        dropDownItems?.forEachIndexed{ index, title->
            var enabled by remember { mutableStateOf(false)}
            enabled = index == selectedIndex
            DropdownMenuItem(
                text = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = spacing.spaceSmall)
                            .clickable {
                                enabled = !enabled
                                selectedIndex = index
                                selectedIndex?.let {
                                    onSelect(it)
                                }
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = null,
                            modifier = Modifier.size(spacing.spaceSmall),
                            tint = if(selectedIndex == index){
                                borderColor
                            }else MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))
                        Text(text = title)
                    }

                },
                onClick = {
                    enabled = !enabled
                    selectedIndex = index
                    selectedIndex?.let {
                        onSelect(it)
                    }
                },
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if(selectedIndex == index) {
                            borderColor
                        }else Color.White
                    ),
                enabled = enabled,
                colors = MenuDefaults.itemColors(
                    textColor = borderColor,
                    disabledTextColor = MaterialTheme.colorScheme.tertiary
                )
            )
            Divider()
        }
    }
}