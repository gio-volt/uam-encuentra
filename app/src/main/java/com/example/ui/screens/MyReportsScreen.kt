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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClaimRequest
import com.example.data.model.ClaimStatus
import com.example.data.model.ItemStatus
import com.example.data.model.ReportedItem
import com.example.data.model.UserRole
import com.example.ui.components.ItemSummaryCard
import com.example.ui.components.StatusBadge
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
import com.example.ui.theme.MidnightNavyDark

@Composable
fun MyReportsScreen(
    currentRole: UserRole,
    myItems: List<ReportedItem>,
    myClaims: List<ClaimRequest>,
    allItems: List<ReportedItem>,
    onSelectItem: (ReportedItem) -> Unit,
    onNavigateToLostForm: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryCanvas)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Summary Card
        item {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(MidnightNavy, shape = RoundedCornerShape(3.dp))
                            .border(0.75.dp, ChampagneGold, RoundedCornerShape(3.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AM",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal,
                            color = ChampagneGold,
                            fontSize = 15.sp,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentRole.displayName.uppercase(),
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 1.sp,
                            color = MidnightNavy,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = currentRole.email,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 11.5.sp,
                            color = EditorialTextMuted
                        )
                    }
                }
            }
        }

        // Mis Solicitudes de Recuperación
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MIS SOLICITUDES DE RECLAMO (${myClaims.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 1.2.sp,
                        color = MidnightNavy,
                        fontSize = 13.sp
                    )
                )
            }
        }

        if (myClaims.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                    border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Assignment,
                            contentDescription = null,
                            tint = ChampagneDark,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "NO HAS ENVIADO SOLICITUDES DE RECLAMO",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.8.sp,
                            fontSize = 12.sp,
                            color = EditorialTextMuted
                        )
                    }
                }
            }
        } else {
            items(myClaims) { claim ->
                val associatedItem = allItems.find { it.id == claim.foundItemId }
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                    border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = (associatedItem?.title ?: "OBJETO SOLICITADO").uppercase(),
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 0.8.sp,
                                color = MidnightNavy,
                                fontSize = 14.sp
                            )
                            val (badgeBg, badgeBorder, badgeText, statusLabel) = when (claim.status) {
                                ClaimStatus.PENDIENTE -> Quadruple(ChampagneLight, ChampagneBorder, ChampagneDark, "EN REVISIÓN")
                                ClaimStatus.APROBADA -> Quadruple(ChampagneLight, ChampagneBorder, EditorialSuccess, "APROBADA")
                                ClaimStatus.RECHAZADA -> Quadruple(Color(0xFFFDF2F2), EditorialError.copy(alpha = 0.4f), EditorialError, "RECHAZADA")
                            }
                            Surface(
                                shape = RoundedCornerShape(2.dp),
                                color = badgeBg,
                                border = androidx.compose.foundation.BorderStroke(0.5.dp, badgeBorder)
                            ) {
                                Text(
                                    text = statusLabel,
                                    color = badgeText,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.5.sp,
                                    fontSize = 9.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (associatedItem != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "UBICACIÓN: ${associatedItem.campusLocation.displayName.uppercase()}",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 11.sp,
                                color = EditorialTextSecondary,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(3.dp),
                            color = LuxuryCanvas,
                            border = androidx.compose.foundation.BorderStroke(0.5.dp, LuxuryBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "TU DECLARACIÓN DE SEGURIDAD:",
                                    fontSize = 9.5.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.6.sp,
                                    color = EditorialTextMuted
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "\"${claim.studentAnswerPrivateDetail}\"",
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = EditorialTextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Mis Objetos Reportados
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MIS REPORTES REALIZADOS (${myItems.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 1.2.sp,
                        color = MidnightNavy,
                        fontSize = 13.sp
                    )
                )
            }
        }

        if (myItems.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                    border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "NO HAS REGISTRADO OBJETOS EXTRAVIADOS",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.8.sp,
                            fontSize = 12.sp,
                            color = EditorialTextMuted
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onNavigateToLostForm,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MidnightNavy,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(3.dp)
                        ) {
                            Text(
                                "REPORTAR EXTRAVÍO",
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.8.sp,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        } else {
            items(myItems) { item ->
                ItemSummaryCard(item = item, onClick = { onSelectItem(item) })
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

