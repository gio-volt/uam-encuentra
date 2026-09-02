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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClaimRequest
import com.example.data.model.ClaimStatus
import com.example.data.model.ItemStatus
import com.example.data.model.ItemType
import com.example.data.model.MatchResult
import com.example.data.model.ReportedItem
import com.example.data.model.UserRole
import com.example.ui.components.ClaimRequestDialog
import com.example.ui.components.ItemTypeBadge
import com.example.ui.components.MatchScoreBadge
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemDetailScreen(
    item: ReportedItem,
    currentRole: UserRole,
    activeClaim: ClaimRequest?,
    matchesForLost: List<MatchResult>,
    onNavigateBack: () -> Unit,
    onSubmitClaim: (answerDetail: String) -> Unit,
    onApproveClaim: (claimId: String) -> Unit,
    onRejectClaim: (claimId: String) -> Unit,
    onSelectMatchingItem: (ReportedItem) -> Unit
) {
    var showClaimDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryCanvas)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Back Button & Status Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = LuxurySurface
                    ),
                    border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                    modifier = Modifier.testTag("detail_back_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = MidnightNavy,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "VOLVER",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        color = MidnightNavy
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ItemTypeBadge(type = item.type)
                    StatusBadge(status = item.status)
                }
            }
        }

        // Main Title Card
        item {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = item.title.uppercase(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 1.2.sp,
                            color = MidnightNavy,
                            fontSize = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 0.75.dp, color = LuxuryBorder)
                    Spacer(modifier = Modifier.height(14.dp))

                    // Attribute Grid
                    DetailAttributeRow(
                        icon = Icons.Default.Category,
                        label = "CATEGORÍA",
                        value = item.category.displayName
                    )
                    DetailAttributeRow(
                        icon = Icons.Default.LocationOn,
                        label = "UBICACIÓN EN CAMPUS",
                        value = "${item.campusLocation.displayName} (${item.campusLocation.zone})"
                    )
                    DetailAttributeRow(
                        icon = Icons.Default.Palette,
                        label = "COLOR Y MARCA",
                        value = "${item.color} • ${item.brand.ifEmpty { "Sin marca registrada" }}"
                    )
                    DetailAttributeRow(
                        icon = Icons.Default.DateRange,
                        label = "FECHA DE REGISTRO",
                        value = item.dateString
                    )
                    DetailAttributeRow(
                        icon = Icons.Default.Person,
                        label = "INFORMADO POR",
                        value = "${item.reportedByName} (${item.reportedByEmail})"
                    )
                }
            }
        }

        // Public Description
        item {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "DESCRIPCIÓN PÚBLICA",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 1.2.sp,
                        color = MidnightNavy,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.publicDescription.ifEmpty { "Sin descripción complementaria disponible." },
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.5.sp,
                        color = EditorialTextSecondary,
                        lineHeight = 19.sp
                    )
                }
            }
        }

        // SEGURIDAD / DETALLE PRIVADO DE CONTROL
        if (item.type == ItemType.ENCONTRADO) {
            item {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (currentRole == UserRole.ADMINISTRADOR) ChampagneLight else LuxurySurface
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        0.75.dp,
                        if (currentRole == UserRole.ADMINISTRADOR) ChampagneBorder else LuxuryBorder
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (currentRole == UserRole.ADMINISTRADOR) Icons.Default.AdminPanelSettings else Icons.Default.Lock,
                                contentDescription = null,
                                tint = ChampagneDark,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (currentRole == UserRole.ADMINISTRADOR)
                                    "CONTROL DE CUSTODIA (ADMINISTRADOR)"
                                else
                                    "VERIFICACIÓN DE SEGURIDAD UAM",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.sp,
                                color = MidnightNavy,
                                fontSize = 12.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (currentRole == UserRole.ADMINISTRADOR) {
                            Text(
                                text = "Detalle privado confidencial asignado al objeto:",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 11.sp,
                                color = EditorialTextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(3.dp),
                                color = LuxurySurface,
                                border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = item.privateControlDetail ?: "Sin detalle confidencial registrado",
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    color = ChampagneDark,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        } else {
                            Text(
                                text = "🔒 Este artículo cuenta con un detalle de control confidencial no visible públicamente. Para reclamar la custodia, deberás responder una pregunta de validación con datos que solo el legítimo propietario conoce.",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 11.5.sp,
                                color = EditorialTextSecondary,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }

            // ACCIÓN DE RECLAMO PARA EL ESTUDIANTE
            if (currentRole == UserRole.ESTUDIANTE) {
                item {
                    when (item.status) {
                        ItemStatus.ACTIVO -> {
                            Button(
                                onClick = { showClaimDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MidnightNavy,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(3.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .testTag("btn_request_claim")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = ChampagneGold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "SOLICITAR RECUPERACIÓN FORMAL",
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp,
                                    fontSize = 11.5.sp
                                )
                            }
                        }

                        ItemStatus.EN_REVISION -> {
                            Surface(
                                shape = RoundedCornerShape(3.dp),
                                color = ChampagneLight,
                                border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = ChampagneDark,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "SOLICITUD EN REVISIÓN",
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Medium,
                                            letterSpacing = 0.8.sp,
                                            color = MidnightNavy,
                                            fontSize = 12.sp
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "El custodio UAM está validando tu descripción confidencial.",
                                            fontFamily = FontFamily.SansSerif,
                                            fontSize = 11.sp,
                                            color = EditorialTextSecondary
                                        )
                                    }
                                }
                            }
                        }

                        ItemStatus.RECUPERADO -> {
                            Surface(
                                shape = RoundedCornerShape(3.dp),
                                color = ChampagneLight,
                                border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = EditorialSuccess,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "ARTÍCULO DEVUELTO A SU PROPIETARIO LEGÍTIMO",
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.SemiBold,
                                        letterSpacing = 0.8.sp,
                                        color = MidnightNavy,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ACCIÓN DE ADMIN SI HAY SOLICITUD ACTIVA
            if (currentRole == UserRole.ADMINISTRADOR && activeClaim != null && activeClaim.status == ClaimStatus.PENDIENTE) {
                item {
                    Card(
                        shape = RoundedCornerShape(4.dp),
                        colors = CardDefaults.cardColors(containerColor = LuxurySurface),
                        border = androidx.compose.foundation.BorderStroke(0.75.dp, ChampagneBorder),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "SOLICITUD DE RECLAMO RECIBIDA",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.sp,
                                color = MidnightNavy,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Solicitante: ${activeClaim.studentName} (${activeClaim.studentEmail})",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 11.5.sp,
                                color = EditorialTextSecondary
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "RESPUESTA ENVIADA POR EL ESTUDIANTE:",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.8.sp,
                                color = MidnightNavy
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                shape = RoundedCornerShape(3.dp),
                                color = ChampagneLight.copy(alpha = 0.5f),
                                border = androidx.compose.foundation.BorderStroke(0.5.dp, ChampagneBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "\"${activeClaim.studentAnswerPrivateDetail}\"",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 12.5.sp,
                                    color = EditorialTextPrimary,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { onApproveClaim(activeClaim.id) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MidnightNavy,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(3.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("admin_approve_claim_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = ChampagneGold,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "APROBAR",
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 11.sp,
                                        letterSpacing = 0.8.sp
                                    )
                                }

                                OutlinedButton(
                                    onClick = { onRejectClaim(activeClaim.id) },
                                    shape = RoundedCornerShape(3.dp),
                                    border = androidx.compose.foundation.BorderStroke(0.75.dp, EditorialError),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("admin_reject_claim_button")
                                ) {
                                    Text(
                                        "RECHAZAR",
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 11.sp,
                                        letterSpacing = 0.8.sp,
                                        color = EditorialError
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // SI ES OBJETO PERDIDO: MOSTRAR COINCIDENCIAS CON ENCONTRADOS
        if (item.type == ItemType.PERDIDO) {
            item {
                Text(
                    text = "AFINIDAD DETECTADA POR EL ALGORITMO",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.2.sp,
                    color = MidnightNavy,
                    fontSize = 13.sp
                )
            }

            if (matchesForLost.isEmpty()) {
                item {
                    Text(
                        text = "No se registran coincidencias deterministas en custodia actualmente.",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 12.sp,
                        color = EditorialTextMuted
                    )
                }
            } else {
                items(matchesForLost) { match ->
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
                                MatchScoreBadge(score = match.score)
                                Text(
                                    text = match.foundItem.dateString.uppercase(),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 10.sp,
                                    color = EditorialTextMuted,
                                    letterSpacing = 0.5.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = match.foundItem.title.uppercase(),
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.sp,
                                color = MidnightNavy,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "UBICACIÓN: ${match.foundItem.campusLocation.displayName.uppercase()}",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 11.sp,
                                color = EditorialTextSecondary,
                                letterSpacing = 0.5.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                match.breakdowns.filter { it.pointsAwarded > 0 }.forEach { rule ->
                                    Surface(
                                        shape = RoundedCornerShape(2.dp),
                                        color = ChampagneLight,
                                        border = androidx.compose.foundation.BorderStroke(0.5.dp, ChampagneBorder)
                                    ) {
                                        Text(
                                            text = "✓ ${rule.ruleName.uppercase()}: +${rule.pointsAwarded}",
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            color = ChampagneDark,
                                            fontWeight = FontWeight.SemiBold,
                                            letterSpacing = 0.5.sp,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { onSelectMatchingItem(match.foundItem) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MidnightNavy,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(3.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "VER OBJETO Y SOLICITAR RECUPERACIÓN",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 11.sp,
                                    letterSpacing = 0.8.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showClaimDialog) {
        ClaimRequestDialog(
            foundItemTitle = item.title,
            onDismiss = { showClaimDialog = false },
            onSubmitClaim = { answer ->
                onSubmitClaim(answer)
                showClaimDialog = false
            }
        )
    }
}

@Composable
fun DetailAttributeRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ChampagneDark,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 9.5.sp,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 0.8.sp,
                fontWeight = FontWeight.SemiBold,
                color = EditorialTextMuted
            )
            Text(
                text = value,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 12.5.sp,
                color = EditorialTextPrimary
            )
        }
    }
}

