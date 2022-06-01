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
import com.commandiron.besonapp_clean_arch.ui.theme.SignUpCompanyBackgroundColor
import com.commandiron.besonapp_clean_arch.ui.theme.SignUpCustomerBackgroundColor

@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    offset: DpOffset,
    dropDownItems: List<String>?,
    onSelect:(Int?) -> Unit,
    onDismissRequest:(Int?) -> Unit,
) {
    val spacing = LocalSpacing.current
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    DropdownMenu(
        modifier = modifier.background(MaterialTheme.colorScheme.tertiary),
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
                                onSelect(selectedIndex)
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = null,
                            modifier = Modifier.size(spacing.spaceSmall),
                            tint = if(selectedIndex == index){
                                MaterialTheme.colorScheme.primary
                            }else MaterialTheme.colorScheme.primaryContainer
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))
                        Text(text = title)
                    }

                },
                onClick = {
                    enabled = !enabled
                    selectedIndex = index
                    onSelect(selectedIndex)
                },
                modifier = Modifier
                    .background(
                        if(selectedIndex == index){
                            SignUpCustomerBackgroundColor
                        }else SignUpCompanyBackgroundColor
                    ),
                enabled = enabled,
                colors = MenuDefaults.itemColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    disabledTextColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
            Divider()
        }
    }
}