package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.ReportedItem
import com.example.ui.components.MetricStatCard
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
import com.example.ui.viewmodel.AdminMetrics

@Composable
fun AdminPanelScreen(
    metrics: AdminMetrics,
    claims: List<ClaimRequest>,
    items: List<ReportedItem>,
    onApproveClaim: (claimId: String) -> Unit,
    onRejectClaim: (claimId: String) -> Unit,
    onSelectItem: (ReportedItem) -> Unit
) {
    val pendingClaims = claims.filter { it.status == ClaimStatus.PENDIENTE }
    val resolvedClaims = claims.filter { it.status != ClaimStatus.PENDIENTE }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryCanvas)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Admin Banner
        item {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = MidnightNavy),
                border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .border(0.75.dp, ChampagneGold, RoundedCornerShape(3.dp))
                            .background(MidnightNavyDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = ChampagneGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "CONTROL Y CUSTODIA",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.2.sp,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Supervisión institucional, cotejo confidencial y actas de entrega",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.SansSerif,
                                color = ChampagneLight,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }

        // Métricas en Cuadrícula (Row 1 y Row 2)
        item {
            Text(
                text = "MÉTRICAS DEL CAMPUS",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.2.sp,
                    color = MidnightNavy,
                    fontSize = 12.5.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Row 1: Total, Perdidos, En Custodia
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricStatCard(
                    title = "TOTAL",
                    value = "${metrics.totalCount}",
                    icon = Icons.Default.Inventory2,
                    accentColor = MidnightNavy,
                    modifier = Modifier.weight(1f)
                )
                MetricStatCard(
                    title = "PERDIDOS",
                    value = "${metrics.lostCount}",
                    icon = Icons.Default.Search,
                    accentColor = EditorialError,
                    modifier = Modifier.weight(1f)
                )
                MetricStatCard(
                    title = "EN CUSTODIA",
                    value = "${metrics.foundCount}",
                    icon = Icons.Default.Security,
                    accentColor = ChampagneDark,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 2: Coincidencias, Por Revisar, Recuperados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricStatCard(
                    title = "AFINIDADES",
                    value = "${metrics.highMatchesCount}",
                    icon = Icons.Default.Star,
                    accentColor = ChampagneDark,
                    modifier = Modifier.weight(1f)
                )
                MetricStatCard(
                    title = "POR REVISAR",
                    value = "${metrics.pendingClaimsCount}",
                    icon = Icons.Default.HourglassTop,
                    accentColor = ChampagneGold,
                    modifier = Modifier.weight(1f)
                )
                MetricStatCard(
                    title = "RECUPERADOS",
                    value = "${metrics.recoveredCount}",
                    icon = Icons.Default.CheckCircle,
                    accentColor = EditorialSuccess,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // SECCIÓN PRINCIPAL: SOLICITUDES PENDIENTES DE VERIFICACIÓN
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "SOLICITUDES PENDIENTES",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 1.2.sp,
                            color = MidnightNavy,
                            fontSize = 13.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (pendingClaims.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(2.dp),
                            color = MidnightNavy,
                            border = androidx.compose.foundation.BorderStroke(0.5.dp, ChampagneGold)
                        ) {
                            Text(
                                text = "${pendingClaims.size}",
                                color = ChampagneGold,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
            }
        }

        if (pendingClaims.isEmpty()) {
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
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.AssignmentTurnedIn,
                            contentDescription = null,
                            tint = ChampagneDark,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "SIN SOLICITUDES PENDIENTES",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 1.sp,
                            color = MidnightNavy,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Cuando un estudiante reclame un artículo en custodia, el protocolo de cotejo confidencial se desplegará aquí.",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 11.5.sp,
                            color = EditorialTextMuted
                        )
                    }
                }
            }
        } else {
            items(pendingClaims) { claim ->
                val associatedItem = items.find { it.id == claim.foundItemId }
                AdminClaimVerificationCard(
                    claim = claim,
                    item = associatedItem,
                    onApprove = { onApproveClaim(claim.id) },
                    onReject = { onRejectClaim(claim.id) },
                    onViewItem = { if (associatedItem != null) onSelectItem(associatedItem) }
                )
            }
        }

        // Historial de solicitudes resueltas
        if (resolvedClaims.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "HISTORIAL DE SOLICITUDES RESUELTAS",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 1.2.sp,
                        color = MidnightNavy,
                        fontSize = 12.5.sp
                    )
                )
            }

            items(resolvedClaims) { claim ->
                val associatedItem = items.find { it.id == claim.foundItemId }
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                    border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = (associatedItem?.title ?: "OBJETO UAM").uppercase(),
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 0.8.sp,
                                color = MidnightNavy,
                                fontSize = 13.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(2.dp),
                                color = if (claim.status == ClaimStatus.APROBADA) ChampagneLight else Color(0xFFFDF2F2),
                                border = androidx.compose.foundation.BorderStroke(
                                    0.5.dp,
                                    if (claim.status == ClaimStatus.APROBADA) ChampagneBorder else EditorialError.copy(alpha = 0.4f)
                                )
                            ) {
                                Text(
                                    text = claim.status.label.uppercase(),
                                    color = if (claim.status == ClaimStatus.APROBADA) ChampagneDark else EditorialError,
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.5.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "SOLICITANTE: ${claim.studentName.uppercase()} (${claim.studentEmail})",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 11.sp,
                            color = EditorialTextSecondary,
                            letterSpacing = 0.4.sp
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun AdminClaimVerificationCard(
    claim: ClaimRequest,
    item: ReportedItem?,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onViewItem: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = LuxurySurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_claim_card_${claim.id}")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header del Reclamo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(2.dp),
                    color = ChampagneLight,
                    border = androidx.compose.foundation.BorderStroke(0.5.dp, ChampagneBorder)
                ) {
                    Text(
                        text = "REVISIÓN PENDIENTE",
                        color = ChampagneDark,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = claim.requestDateString.uppercase(),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 10.sp,
                    color = EditorialTextMuted,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Datos del Objeto
            Text(
                text = (item?.title ?: "OBJETO ENCONTRADO").uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.sp,
                    color = MidnightNavy,
                    fontSize = 14.sp
                )
            )
            if (item != null) {
                Text(
                    text = "UBICACIÓN: ${item.campusLocation.displayName.uppercase()} • ${item.dateString}",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 11.sp,
                    color = EditorialTextSecondary,
                    letterSpacing = 0.4.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = ChampagneDark,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SOLICITANTE: ${claim.studentName.uppercase()} (${claim.studentEmail})",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.4.sp,
                    color = MidnightNavy
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // COMPARATIVA LADO A LADO / CAJA DE VERIFICACIÓN
            Surface(
                shape = RoundedCornerShape(3.dp),
                color = LuxuryCanvas,
                border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "COTEJO DE SEGURIDAD CONFIDENCIAL",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 1.sp,
                        fontSize = 11.5.sp,
                        color = MidnightNavy
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // 1. Detalle Privado Oculto
                    Surface(
                        shape = RoundedCornerShape(3.dp),
                        color = ChampagneLight,
                        border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = ChampagneDark,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "DETALLE DE CONTROL (EN CUSTODIA):",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily.SansSerif,
                                    letterSpacing = 0.6.sp,
                                    fontSize = 10.sp,
                                    color = ChampagneDark
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item?.privateControlDetail ?: "No asignado",
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.5.sp,
                                color = EditorialTextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 2. Respuesta dada por el estudiante
                    Surface(
                        shape = RoundedCornerShape(3.dp),
                        color = LuxurySurface,
                        border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "RESPUESTA DECLARADA POR ${claim.studentName.uppercase()}:",
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif,
                                letterSpacing = 0.6.sp,
                                fontSize = 10.sp,
                                color = MidnightNavy
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"${claim.studentAnswerPrivateDetail}\"",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 12.5.sp,
                                color = EditorialTextSecondary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de Acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onApprove,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MidnightNavy,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(3.dp),
                    modifier = Modifier
                        .weight(1.3f)
                        .testTag("btn_approve_claim_${claim.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = ChampagneGold,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "APROBAR Y ENTREGAR",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        fontSize = 11.sp
                    )
                }

                OutlinedButton(
                    onClick = onReject,
                    shape = RoundedCornerShape(3.dp),
                    border = androidx.compose.foundation.BorderStroke(0.75.dp, EditorialError),
                    modifier = Modifier
                        .weight(0.9f)
                        .testTag("btn_reject_claim_${claim.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = EditorialError,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "RECHAZAR",
                        fontFamily = FontFamily.SansSerif,
                        letterSpacing = 0.8.sp,
                        color = EditorialError,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
