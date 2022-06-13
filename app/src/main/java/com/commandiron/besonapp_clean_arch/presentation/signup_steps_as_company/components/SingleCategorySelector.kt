package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.presentation.components.CategoryItem
import com.commandiron.besonapp_clean_arch.presentation.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun SingleCategorySelector(
    itemListMain: List<MainConstructionItem>,
    selectedMainConstructionItem: MainConstructionItem?,
    onItemSelected:(MainConstructionItem) -> Unit
) {
    val spacing = LocalSpacing.current
    var selectedItem by remember { mutableStateOf(selectedMainConstructionItem) }
    LazyVerticalGrid(
        columns =  GridCells.Fixed(3),
        content = {
            items(itemListMain) { item ->
                var isSelected by remember { mutableStateOf(false)}
                isSelected = item == selectedItem
                CategoryItem(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .border(1.dp, MaterialTheme.colorScheme.onBackground)
                        .clickable {
                            selectedItem = item
                            onItemSelected(item)
                        },
                    itemMain = item
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(spacing.spaceSmall),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Icon(
                            imageVector = if(isSelected) {
                                Icons.Outlined.CheckCircleOutline
                            } else {
                                Icons.Outlined.Circle},
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(14.dp),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )

}