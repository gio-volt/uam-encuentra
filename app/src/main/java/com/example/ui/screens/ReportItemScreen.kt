package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CampusLocation
import com.example.data.model.ItemCategory
import com.example.data.model.ItemType
import com.example.ui.theme.ChampagneBorder
import com.example.ui.theme.ChampagneDark
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.ChampagneLight
import com.example.ui.theme.EditorialBlack
import com.example.ui.theme.EditorialError
import com.example.ui.theme.EditorialSuccess
import com.example.ui.theme.EditorialTextMuted
import com.example.ui.theme.EditorialTextPrimary
import com.example.ui.theme.EditorialTextSecondary
import com.example.ui.theme.LuxuryBorder
import com.example.ui.theme.LuxuryCanvas
import com.example.ui.theme.LuxurySurface
import com.example.ui.theme.MidnightNavy

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ReportItemScreen(
    itemType: ItemType,
    onNavigateBack: () -> Unit,
    onSubmitLost: (
        title: String,
        category: ItemCategory,
        color: String,
        brand: String,
        location: CampusLocation,
        date: String,
        description: String
    ) -> Unit,
    onSubmitFound: (
        title: String,
        category: ItemCategory,
        color: String,
        brand: String,
        location: CampusLocation,
        date: String,
        description: String,
        privateDetail: String
    ) -> Unit
) {
    val isLost = itemType == ItemType.PERDIDO

    var title by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ItemCategory.ELECTRONICA) }
    var color by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf(CampusLocation.BIBLIOTECA) }
    var locationExpanded by remember { mutableStateOf(false) }
    var dateString by remember { mutableStateOf("Hoy") }
    var description by remember { mutableStateOf("") }
    var privateControlDetail by remember { mutableStateOf("") }

    var titleError by remember { mutableStateOf(false) }
    var colorError by remember { mutableStateOf(false) }
    var privateDetailError by remember { mutableStateOf(false) }

    fun fillDemoPresentationCase() {
        title = "Laptop Lenovo gris"
        selectedCategory = ItemCategory.ELECTRONICA
        color = "Gris"
        brand = "Lenovo"
        selectedLocation = CampusLocation.BIBLIOTECA
        dateString = "Ayer"
        description = "Olvidé mi laptop Lenovo color gris luego de estudiar en el segundo piso de la biblioteca cerca del ventanal."
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryCanvas)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isLost) "REGISTRAR OBJETO EXTRAVIADO" else "REGISTRAR OBJETO ENCONTRADO",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.5.sp,
                                color = MidnightNavy
                            )
                        )
                        Surface(
                            shape = RoundedCornerShape(2.dp),
                            color = ChampagneLight,
                            border = androidx.compose.foundation.BorderStroke(0.5.dp, ChampagneBorder)
                        ) {
                            Text(
                                text = if (isLost) "BÚSQUEDA" else "CUSTODIA",
                                fontSize = 9.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.8.sp,
                                color = ChampagneDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isLost)
                            "Ingresa los detalles para que el algoritmo determinista lo compare inmediatamente con los objetos bajo custodia en el campus."
                        else
                            "Registra el objeto hallado en el campus UAM. El detalle confidencial de control servirá para validar la identidad del dueño.",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        color = EditorialTextSecondary,
                        lineHeight = 18.sp
                    )

                    // Quick Demo auto-fill button for Presentation
                    if (isLost) {
                        Spacer(modifier = Modifier.height(14.dp))
                        OutlinedButton(
                            onClick = { fillDemoPresentationCase() },
                            shape = RoundedCornerShape(3.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = ChampagneLight
                            ),
                            border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_autofill_demo_case")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = ChampagneDark,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AUTOCOMPLETAR CASO DEMO (LAPTOP LENOVO)",
                                color = ChampagneDark,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.8.sp,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // Título / Nombre
        item {
            Column {
                Text(
                    text = "DENOMINACIÓN DEL ARTÍCULO *",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    fontSize = 11.sp,
                    color = MidnightNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = false
                    },
                    placeholder = {
                        Text(
                            "Ej: Laptop Lenovo gris, Carnet UAM, Llaves con jaguar",
                            fontSize = 12.5.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = EditorialTextMuted
                        )
                    },
                    isError = titleError,
                    supportingText = {
                        if (titleError) Text("El nombre es requerido.", color = EditorialError, fontSize = 11.sp)
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
                        .testTag("input_item_title")
                )
            }
        }

        // Categoría (Filter chips)
        item {
            Column {
                Text(
                    text = "CATEGORÍA DEL OBJETO *",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    fontSize = 11.sp,
                    color = MidnightNavy
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ItemCategory.values().forEach { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            shape = RoundedCornerShape(3.dp),
                            label = {
                                Text(
                                    text = cat.displayName.uppercase(),
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    letterSpacing = 0.8.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
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
                                selected = isSelected,
                                borderColor = if (isSelected) MidnightNavy else LuxuryBorder,
                                borderWidth = 0.75.dp
                            ),
                            modifier = Modifier.testTag("chip_category_${cat.name}")
                        )
                    }
                }
            }
        }

        // Color y Marca (En fila)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "COLOR PRINCIPAL *",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        fontSize = 10.5.sp,
                        color = MidnightNavy
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = color,
                        onValueChange = {
                            color = it
                            colorError = false
                        },
                        placeholder = { Text("Gris, Azul...", fontSize = 12.sp, color = EditorialTextMuted) },
                        isError = colorError,
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
                            .testTag("input_item_color")
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "MARCA / FABRICANTE",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        fontSize = 10.5.sp,
                        color = MidnightNavy
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = brand,
                        onValueChange = { brand = it },
                        placeholder = { Text("Lenovo, Apple...", fontSize = 12.sp, color = EditorialTextMuted) },
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
                            .testTag("input_item_brand")
                    )
                }
            }
        }

        // Lugar del Campus (Dropdown)
        item {
            Column {
                Text(
                    text = "UBICACIÓN EN CAMPUS UAM *",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    fontSize = 11.sp,
                    color = MidnightNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                ExposedDropdownMenuBox(
                    expanded = locationExpanded,
                    onExpandedChange = { locationExpanded = !locationExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = "${selectedLocation.displayName.uppercase()} — ${selectedLocation.zone.uppercase()}",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = locationExpanded) },
                        shape = RoundedCornerShape(3.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MidnightNavy,
                            unfocusedBorderColor = LuxuryBorder,
                            focusedContainerColor = LuxurySurface,
                            unfocusedContainerColor = LuxurySurface
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .testTag("input_item_location")
                    )

                    ExposedDropdownMenu(
                        expanded = locationExpanded,
                        onDismissRequest = { locationExpanded = false },
                        modifier = Modifier.background(LuxurySurface)
                    ) {
                        CampusLocation.values().forEach { loc ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            loc.displayName.uppercase(),
                                            fontFamily = FontFamily.SansSerif,
                                            fontWeight = FontWeight.SemiBold,
                                            letterSpacing = 0.8.sp,
                                            fontSize = 11.sp,
                                            color = MidnightNavy
                                        )
                                        Text(
                                            loc.zone,
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            color = EditorialTextMuted
                                        )
                                    }
                                },
                                onClick = {
                                    selectedLocation = loc
                                    locationExpanded = false
                                },
                                modifier = Modifier.testTag("dropdown_location_${loc.name}")
                            )
                        }
                    }
                }
            }
        }

        // Fecha
        item {
            Column {
                Text(
                    text = "FECHA APROXIMADA",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    fontSize = 11.sp,
                    color = MidnightNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = dateString,
                    onValueChange = { dateString = it },
                    placeholder = { Text("Hoy, Ayer, Hace 2 días...", fontSize = 12.sp, color = EditorialTextMuted) },
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
                        .testTag("input_item_date")
                )
            }
        }

        // Descripción pública
        item {
            Column {
                Text(
                    text = "DESCRIPCIÓN PÚBLICA / CARACTERÍSTICAS VISIBLES",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    fontSize = 11.sp,
                    color = MidnightNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = {
                        Text(
                            "Detalles generales, condición visible, aula o espacio donde se extravió...",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = EditorialTextMuted
                        )
                    },
                    minLines = 3,
                    maxLines = 4,
                    shape = RoundedCornerShape(3.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MidnightNavy,
                        unfocusedBorderColor = LuxuryBorder,
                        focusedContainerColor = LuxurySurface,
                        unfocusedContainerColor = LuxurySurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_item_description")
                )
            }
        }

        // CAMPO ESPECIAL: DETALLE PRIVADO DE CONTROL (SOLO PARA OBJETOS ENCONTRADOS)
        if (!isLost) {
            item {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = ChampagneLight),
                    border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(ChampagneGold.copy(alpha = 0.35f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = ChampagneDark,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "DETALLE CONFIDENCIAL DE CONTROL UAM",
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 1.sp,
                                    color = MidnightNavy,
                                    fontSize = 12.5.sp
                                )
                                Text(
                                    text = "🔒 NUNCA SE MOSTRARÁ AL PÚBLICO",
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.8.sp,
                                    color = ChampagneDark,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Anota aquí una característica oculta que solo el verdadero dueño sabría (ej: sticker en esquina inferior, contraseña de bloqueo, contenido interior, etc.).",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 11.5.sp,
                            color = EditorialTextSecondary,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = privateControlDetail,
                            onValueChange = {
                                privateControlDetail = it
                                privateDetailError = false
                            },
                            placeholder = {
                                Text(
                                    "Ej: Sticker azul pequeño, grabado de iniciales...",
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = EditorialTextMuted
                                )
                            },
                            isError = privateDetailError,
                            supportingText = {
                                if (privateDetailError) Text("Por favor especifica un detalle de control para la custodia.", color = EditorialError, fontSize = 11.sp)
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(3.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MidnightNavy,
                                unfocusedBorderColor = ChampagneBorder,
                                focusedContainerColor = LuxurySurface,
                                unfocusedContainerColor = LuxurySurface
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_private_control_detail")
                        )
                    }
                }
            }
        }

        // Botón de Envío
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    var hasErrors = false
                    if (title.trim().isEmpty()) {
                        titleError = true
                        hasErrors = true
                    }
                    if (color.trim().isEmpty()) {
                        colorError = true
                        hasErrors = true
                    }
                    if (!isLost && privateControlDetail.trim().isEmpty()) {
                        privateDetailError = true
                        hasErrors = true
                    }

                    if (!hasErrors) {
                        if (isLost) {
                            onSubmitLost(
                                title.trim(),
                                selectedCategory,
                                color.trim(),
                                brand.trim(),
                                selectedLocation,
                                dateString.trim().ifEmpty { "Hoy" },
                                description.trim()
                            )
                        } else {
                            onSubmitFound(
                                title.trim(),
                                selectedCategory,
                                color.trim(),
                                brand.trim(),
                                selectedLocation,
                                dateString.trim().ifEmpty { "Hoy" },
                                description.trim(),
                                privateControlDetail.trim()
                            )
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLost) ChampagneGold else MidnightNavy,
                    contentColor = if (isLost) EditorialBlack else Color.White
                ),
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("btn_submit_report")
            ) {
                Icon(
                    imageVector = if (isLost) Icons.Default.Search else Icons.Default.Check,
                    contentDescription = null,
                    tint = if (isLost) EditorialBlack else ChampagneGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isLost) "REGISTRAR EXTRAVÍO Y EVALUAR" else "GUARDAR EN CUSTODIA UAM",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp,
                    fontSize = 12.sp,
                    color = if (isLost) EditorialBlack else Color.White
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

