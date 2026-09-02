package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ReportedItem
import com.example.data.model.UserRole
import com.example.ui.components.ItemSummaryCard
import com.example.ui.theme.ChampagneBorder
import com.example.ui.theme.ChampagneDark
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.ChampagneLight
import com.example.ui.theme.EditorialBlack
import com.example.ui.theme.EditorialSuccess
import com.example.ui.theme.EditorialTextMuted
import com.example.ui.theme.EditorialTextPrimary
import com.example.ui.theme.EditorialTextSecondary
import com.example.ui.theme.LuxuryBorder
import com.example.ui.theme.LuxuryCanvas
import com.example.ui.theme.LuxurySurface
import com.example.ui.theme.MidnightNavy
import com.example.ui.viewmodel.AdminMetrics

@Composable
fun HomeScreen(
    currentRole: UserRole,
    items: List<ReportedItem>,
    metrics: AdminMetrics,
    onNavigateToLostForm: () -> Unit,
    onNavigateToFoundForm: () -> Unit,
    onNavigateToBrowseFound: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    onSelectItem: (ReportedItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryCanvas)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))
            // Hero Welcome Banner with Editorial Luxury styling
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = MidnightNavy),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneGold.copy(alpha = 0.35f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_hero_card")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(2.dp),
                                color = Color.Transparent,
                                border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneGold.copy(alpha = 0.8f))
                            ) {
                                Text(
                                    text = "CAMPUS UAM MANAGUA",
                                    color = ChampagneGold,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.5.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = "SESIÓN: ${currentRole.displayName.uppercase()}",
                                color = ChampagneGold.copy(alpha = 0.8f),
                                fontSize = 10.sp,
                                fontFamily = FontFamily.SansSerif,
                                letterSpacing = 1.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "UAM ENCUENTRA",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 2.5.sp,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "La forma rápida y segura de recuperar lo que perdiste en la universidad.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Light,
                                color = Color.White.copy(alpha = 0.85f),
                                lineHeight = 21.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        // Quick action buttons: "Perdí algo" and "Encontré algo"
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = onNavigateToLostForm,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ChampagneGold,
                                    contentColor = EditorialBlack
                                ),
                                shape = RoundedCornerShape(3.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_lost_something")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = EditorialBlack,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "PERDÍ ALGO",
                                    color = EditorialBlack,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp,
                                    fontSize = 11.5.sp
                                )
                            }

                            OutlinedButton(
                                onClick = onNavigateToFoundForm,
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                border = androidx.compose.foundation.BorderStroke(0.75.dp, Color.White.copy(alpha = 0.6f)),
                                shape = RoundedCornerShape(3.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_found_something")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = ChampagneGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "ENCONTRÉ ALGO",
                                    color = Color.White,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp,
                                    fontSize = 11.5.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Caso de Presentación Destacado (Editorial Card)
        item {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = ChampagneLight),
                border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("presentation_case_card")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(ChampagneGold.copy(alpha = 0.25f))
                            .border(0.5.dp, ChampagneGold, RoundedCornerShape(3.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = ChampagneDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CASO DEMO: LAPTOP LENOVO",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp,
                            color = MidnightNavy,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Hay una Laptop Lenovo gris en Biblioteca con detalle de control. Repórtala en 'Perdí algo' para experimentar la coincidencia determinista ~90%.",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 12.sp,
                            color = EditorialTextSecondary,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // Acceso rápido a Objetos Encontrados
        item {
            Surface(
                onClick = onNavigateToBrowseFound,
                shape = RoundedCornerShape(4.dp),
                color = LuxurySurface,
                border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                shadowElevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quick_access_browse_found")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(ChampagneLight)
                                .border(0.5.dp, ChampagneBorder, RoundedCornerShape(3.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                tint = ChampagneDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "OBJETOS EN CUSTODIA",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Normal,
                                    letterSpacing = 1.2.sp,
                                    color = MidnightNavy
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${metrics.foundCount} artículos bajo resguardo en campus",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontFamily = FontFamily.SansSerif,
                                    color = EditorialTextSecondary
                                )
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Ir",
                        tint = ChampagneDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Resumen del Proceso UAM (Reporta -> Coincide -> Verifica -> Recupera)
        item {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "PROTOCOLO DE RECUPERACIÓN UAM",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 1.5.sp,
                            color = MidnightNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Flujo institucional con detalle privado de control",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 11.5.sp,
                        color = EditorialTextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProcessStepItem(
                            stepNumber = "1",
                            title = "REPORTA",
                            desc = "Lugar y fotos",
                            color = MidnightNavy
                        )
                        ProcessStepDivider()
                        ProcessStepItem(
                            stepNumber = "2",
                            title = "COINCIDE",
                            desc = "Motor UAM",
                            color = ChampagneDark
                        )
                        ProcessStepDivider()
                        ProcessStepItem(
                            stepNumber = "3",
                            title = "VERIFICA",
                            desc = "Detalle oculto",
                            color = MidnightNavy
                        )
                        ProcessStepDivider()
                        ProcessStepItem(
                            stepNumber = "4",
                            title = "RECUPERA",
                            desc = "Entrega segura",
                            color = EditorialSuccess
                        )
                    }
                }
            }
        }

        // Métricas rápidas
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickMetricChip(
                    label = "EN CUSTODIA",
                    count = "${metrics.foundCount}",
                    color = MidnightNavy,
                    modifier = Modifier.weight(1f)
                )
                QuickMetricChip(
                    label = "COINCIDENCIAS",
                    count = "${metrics.highMatchesCount}",
                    color = ChampagneDark,
                    modifier = Modifier.weight(1f)
                )
                QuickMetricChip(
                    label = "RECUPERADOS",
                    count = "${metrics.recoveredCount}",
                    color = EditorialSuccess,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Objetos recientemente reportados
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ARTÍCULOS RECIENTES",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 1.8.sp,
                        color = MidnightNavy
                    )
                )
                Text(
                    text = "VER TODOS",
                    color = ChampagneDark,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.clickable { onNavigateToBrowseFound() }
                )
            }
        }

        val displayItems = items.take(4)
        items(displayItems) { item ->
            ItemSummaryCard(item = item, onClick = { onSelectItem(item) })
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProcessStepItem(
    stepNumber: String,
    title: String,
    desc: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(68.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color.copy(alpha = 0.08f))
                .border(0.75.dp, color.copy(alpha = 0.35f), RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 13.sp
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.8.sp,
            fontSize = 10.sp,
            color = MidnightNavy
        )
        Text(
            text = desc,
            fontFamily = FontFamily.SansSerif,
            fontSize = 9.sp,
            color = EditorialTextMuted,
            maxLines = 1
        )
    }
}

@Composable
fun ProcessStepDivider() {
    Box(
        modifier = Modifier
            .padding(top = 15.dp)
            .height(0.75.dp)
            .width(16.dp)
            .background(ChampagneBorder)
    )
}

@Composable
fun QuickMetricChip(
    label: String,
    count: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = LuxurySurface,
        border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 9.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                color = EditorialTextMuted
            )
        }
    }
}


