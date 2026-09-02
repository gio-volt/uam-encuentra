package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ItemCategory
import com.example.data.model.ItemType
import com.example.data.model.ReportedItem
import com.example.ui.components.ItemSummaryCard
import com.example.ui.theme.ChampagneBorder
import com.example.ui.theme.ChampagneDark
import com.example.ui.theme.ChampagneLight
import com.example.ui.theme.EditorialTextMuted
import com.example.ui.theme.EditorialTextPrimary
import com.example.ui.theme.EditorialTextSecondary
import com.example.ui.theme.LuxuryBorder
import com.example.ui.theme.LuxuryCanvas
import com.example.ui.theme.LuxurySurface
import com.example.ui.theme.MidnightNavy

@Composable
fun ItemsListScreen(
    items: List<ReportedItem>,
    onSelectItem: (ReportedItem) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf<ItemCategory?>(null) }
    var onlyFoundFilter by remember { mutableStateOf(true) }

    val filteredItems = items.filter { item ->
        val matchesType = if (onlyFoundFilter) item.type == ItemType.ENCONTRADO else true
        val matchesCategory = selectedCategoryFilter == null || item.category == selectedCategoryFilter
        val matchesQuery = searchQuery.trim().isEmpty() ||
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.brand.contains(searchQuery, ignoreCase = true) ||
                item.color.contains(searchQuery, ignoreCase = true) ||
                item.campusLocation.displayName.contains(searchQuery, ignoreCase = true) ||
                item.publicDescription.contains(searchQuery, ignoreCase = true)

        matchesType && matchesCategory && matchesQuery
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryCanvas)
    ) {
        // Search Bar & Filter Header
        Surface(
            color = LuxurySurface,
            border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "Buscar por objeto, marca, color o ubicación...",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = EditorialTextMuted
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = ChampagneDark,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpiar",
                                    tint = EditorialTextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(3.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MidnightNavy,
                        unfocusedBorderColor = LuxuryBorder,
                        focusedContainerColor = LuxurySurface,
                        unfocusedContainerColor = LuxurySurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_items_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Type Toggle: Solo Encontrados vs Todos
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterChip(
                        selected = onlyFoundFilter,
                        onClick = { onlyFoundFilter = true },
                        shape = RoundedCornerShape(3.dp),
                        label = {
                            Text(
                                "EN CUSTODIA",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.8.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MidnightNavy,
                            selectedLabelColor = Color.White,
                            containerColor = LuxurySurface,
                            labelColor = EditorialTextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = onlyFoundFilter,
                            borderColor = if (onlyFoundFilter) MidnightNavy else LuxuryBorder,
                            borderWidth = 0.75.dp
                        ),
                        modifier = Modifier.testTag("filter_only_found")
                    )

                    FilterChip(
                        selected = !onlyFoundFilter,
                        onClick = { onlyFoundFilter = false },
                        shape = RoundedCornerShape(3.dp),
                        label = {
                            Text(
                                "TODOS LOS REPORTES",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.8.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MidnightNavy,
                            selectedLabelColor = Color.White,
                            containerColor = LuxurySurface,
                            labelColor = EditorialTextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = !onlyFoundFilter,
                            borderColor = if (!onlyFoundFilter) MidnightNavy else LuxuryBorder,
                            borderWidth = 0.75.dp
                        ),
                        modifier = Modifier.testTag("filter_all_reports")
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Category Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(vertical = 2.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == null,
                            onClick = { selectedCategoryFilter = null },
                            shape = RoundedCornerShape(3.dp),
                            label = {
                                Text(
                                    "TODAS",
                                    fontSize = 9.5.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    letterSpacing = 0.8.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ChampagneLight,
                                selectedLabelColor = ChampagneDark,
                                containerColor = LuxurySurface,
                                labelColor = EditorialTextMuted
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selectedCategoryFilter == null,
                                borderColor = if (selectedCategoryFilter == null) ChampagneBorder else LuxuryBorder,
                                borderWidth = 0.75.dp
                            ),
                            modifier = Modifier.testTag("filter_category_all")
                        )
                    }

                    items(ItemCategory.values()) { category ->
                        val isSelected = selectedCategoryFilter == category
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedCategoryFilter =
                                    if (isSelected) null else category
                            },
                            shape = RoundedCornerShape(3.dp),
                            label = {
                                Text(
                                    category.displayName.uppercase(),
                                    fontSize = 9.5.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    letterSpacing = 0.8.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ChampagneLight,
                                selectedLabelColor = ChampagneDark,
                                containerColor = LuxurySurface,
                                labelColor = EditorialTextMuted
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) ChampagneBorder else LuxuryBorder,
                                borderWidth = 0.75.dp
                            ),
                            modifier = Modifier.testTag("filter_category_${category.name}")
                        )
                    }
                }
            }
        }

        // List Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CATÁLOGO DE REGISTROS (${filteredItems.size})",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 1.2.sp,
                        color = MidnightNavy,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "DETALLE Y RECLAMO",
                        fontFamily = FontFamily.SansSerif,
                        letterSpacing = 0.8.sp,
                        color = ChampagneDark,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (filteredItems.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(4.dp),
                        colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                        border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(36.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                tint = ChampagneBorder,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "SIN REGISTROS COINCIDENTES",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.sp,
                                color = MidnightNavy,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "No se encontraron artículos con los filtros aplicados.",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 12.sp,
                                color = EditorialTextMuted
                            )
                        }
                    }
                }
            } else {
                items(filteredItems) { item ->
                    ItemSummaryCard(item = item, onClick = { onSelectItem(item) })
                }
            }
        }
    }
}

