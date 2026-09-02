package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ItemStatus
import com.example.data.model.ItemType
import com.example.data.model.MatchResult
import com.example.data.model.ReportedItem
import com.example.data.model.UserRole
import com.example.ui.theme.ChampagneBorder
import com.example.ui.theme.ChampagneDark
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.ChampagneLight
import com.example.ui.theme.EditorialBlack
import com.example.ui.theme.EditorialError
import com.example.ui.theme.EditorialErrorContainer
import com.example.ui.theme.EditorialSuccess
import com.example.ui.theme.EditorialSuccessContainer
import com.example.ui.theme.EditorialTextMuted
import com.example.ui.theme.EditorialTextPrimary
import com.example.ui.theme.EditorialTextSecondary
import com.example.ui.theme.LuxuryBorder
import com.example.ui.theme.LuxuryBorderSubtle
import com.example.ui.theme.LuxuryCanvas
import com.example.ui.theme.LuxurySurface
import com.example.ui.theme.MidnightNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UamTopAppBar(
    currentRole: UserRole,
    onRoleChange: (UserRole) -> Unit,
    onResetDemo: () -> Unit,
    canNavigateBack: Boolean = false,
    onNavigateBack: () -> Unit = {}
) {
    var roleMenuExpanded by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    Surface(
        color = MidnightNavy,
        border = androidx.compose.foundation.BorderStroke(0.5.dp, ChampagneGold.copy(alpha = 0.25f))
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MidnightNavy,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("nav_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = ChampagneGold
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .size(36.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.White.copy(alpha = 0.08f))
                            .border(0.75.dp, ChampagneGold.copy(alpha = 0.6f), RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "UAM Logo",
                            tint = ChampagneGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            },
            title = {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "UAM ENCUENTRA",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 2.2.sp
                            ),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (currentRole == UserRole.ADMINISTRADOR) ChampagneGold else Color.White.copy(alpha = 0.15f))
                                .border(0.5.dp, ChampagneGold, RoundedCornerShape(3.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (currentRole == UserRole.ADMINISTRADOR) "ADMIN" else "ESTUDIANTE",
                                fontSize = 8.5.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = if (currentRole == UserRole.ADMINISTRADOR) EditorialBlack else Color.White
                            )
                        }
                    }
                    Text(
                        text = "UNIVERSIDAD AMERICANA • MANAGUA",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 9.sp,
                            letterSpacing = 1.2.sp,
                            fontWeight = FontWeight.Light
                        ),
                        color = ChampagneGold.copy(alpha = 0.9f)
                    )
                }
            },
            actions = {
                // Role Selector Button
                Box {
                    Surface(
                        onClick = { roleMenuExpanded = true },
                        shape = RoundedCornerShape(4.dp),
                        color = Color.Transparent,
                        border = androidx.compose.foundation.BorderStroke(
                            0.75.dp,
                            if (currentRole == UserRole.ADMINISTRADOR) ChampagneGold else Color.White.copy(alpha = 0.35f)
                        ),
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .testTag("role_selector_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (currentRole == UserRole.ADMINISTRADOR) Icons.Default.AdminPanelSettings else Icons.Default.Person,
                                contentDescription = "Rol actual",
                                tint = ChampagneGold,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (currentRole == UserRole.ADMINISTRADOR) "ADMIN" else "ANA",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = roleMenuExpanded,
                        onDismissRequest = { roleMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(
                                        text = "Ana Martínez",
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "estudiante@uam.edu.ni (Estudiante)",
                                        fontSize = 11.sp,
                                        color = EditorialTextSecondary
                                    )
                                }
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = MidnightNavy
                                )
                            },
                            onClick = {
                                onRoleChange(UserRole.ESTUDIANTE)
                                roleMenuExpanded = false
                            },
                            modifier = Modifier.testTag("select_student_role")
                        )

                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(
                                        text = "Admin UAM",
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "admin@uam.edu.ni (Administrador)",
                                        fontSize = 11.sp,
                                        color = EditorialTextSecondary
                                    )
                                }
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = ChampagneDark
                                )
                            },
                            onClick = {
                                onRoleChange(UserRole.ADMINISTRADOR)
                                roleMenuExpanded = false
                            },
                            modifier = Modifier.testTag("select_admin_role")
                        )
                    }
                }

                // Reset Demo Button
                IconButton(
                    onClick = { showResetConfirmDialog = true },
                    modifier = Modifier.testTag("reset_demo_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reiniciar datos demo",
                        tint = ChampagneGold.copy(alpha = 0.9f)
                    )
                }
            }
        )
    }

    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            shape = RoundedCornerShape(4.dp),
            containerColor = LuxurySurface,
            title = {
                Text(
                    text = "REINICIAR DEMO",
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Normal,
                    color = MidnightNavy
                )
            },
            text = {
                Text(
                    text = "Se restablecerán todos los objetos de prueba iniciales (incluyendo la 'Laptop Lenovo gris' en Biblioteca y las métricas originales).",
                    fontFamily = FontFamily.SansSerif,
                    color = EditorialTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onResetDemo()
                        showResetConfirmDialog = false
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MidnightNavy),
                    modifier = Modifier.testTag("confirm_reset_button")
                ) {
                    Text(
                        "REINICIAR",
                        fontFamily = FontFamily.SansSerif,
                        letterSpacing = 1.sp,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text(
                        "CANCELAR",
                        fontFamily = FontFamily.SansSerif,
                        letterSpacing = 1.sp,
                        fontSize = 12.sp,
                        color = EditorialTextSecondary
                    )
                }
            }
        )
    }
}

private fun border(width: androidx.compose.ui.unit.Dp, color: Color) =
    androidx.compose.foundation.BorderStroke(width, color)

@Composable
fun StatusBadge(status: ItemStatus) {
    val (bgColor, textColor, borderColor, label) = when (status) {
        ItemStatus.ACTIVO -> Quadruple(
            MidnightNavy.copy(alpha = 0.05f),
            MidnightNavy,
            MidnightNavy.copy(alpha = 0.2f),
            "EN CUSTODIA"
        )
        ItemStatus.EN_REVISION -> Quadruple(
            ChampagneLight,
            ChampagneDark,
            ChampagneBorder,
            "EN REVISIÓN"
        )
        ItemStatus.RECUPERADO -> Quadruple(
            EditorialSuccessContainer,
            EditorialSuccess,
            EditorialSuccess.copy(alpha = 0.3f),
            "RECUPERADO"
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(bgColor)
            .border(0.75.dp, borderColor, RoundedCornerShape(3.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 10.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.8.sp
        )
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
fun ItemTypeBadge(type: ItemType) {
    val isPerdido = type == ItemType.PERDIDO
    val bgColor = if (isPerdido) EditorialErrorContainer else ChampagneLight
    val textColor = if (isPerdido) EditorialError else ChampagneDark
    val borderColor = if (isPerdido) EditorialError.copy(alpha = 0.25f) else ChampagneBorder
    val label = if (isPerdido) "PERDIDO" else "ENCONTRADO"

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(bgColor)
            .border(0.75.dp, borderColor, RoundedCornerShape(3.dp))
            .padding(horizontal = 7.dp, vertical = 2.5.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 9.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun MatchScoreBadge(score: Int, modifier: Modifier = Modifier) {
    val isHigh = score >= 80
    val bgColor = if (isHigh) ChampagneLight else LuxuryCanvas
    val contentColor = if (isHigh) ChampagneDark else MidnightNavy
    val borderColor = if (isHigh) ChampagneBorder else LuxuryBorder

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = bgColor,
        border = border(0.75.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = ChampagneGold,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = if (isHigh) "AFINIDAD ALTA • $score%" else "COINCIDENCIA • $score%",
                color = contentColor,
                fontSize = 11.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
        }
    }
}

@Composable
fun MetricStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = LuxurySurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = border(0.75.dp, LuxuryBorder)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(ChampagneLight)
                        .border(0.5.dp, ChampagneBorder, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = ChampagneDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        color = MidnightNavy
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    color = MidnightNavy
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 11.sp,
                        color = EditorialTextMuted
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HighMatchAlertDialog(
    matchResult: MatchResult,
    onDismiss: () -> Unit,
    onViewAndClaim: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(4.dp),
        containerColor = LuxurySurface,
        icon = {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(ChampagneLight)
                    .border(0.75.dp, ChampagneBorder, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = ChampagneDark,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "COINCIDENCIA DETECTADA",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 1.8.sp,
                        textAlign = TextAlign.Center
                    ),
                    color = MidnightNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                MatchScoreBadge(score = matchResult.score)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "El sistema ha detectado un objeto en custodia con alta afinidad a tu reporte de extravío:",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        color = EditorialTextSecondary,
                        lineHeight = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = ChampagneLight.copy(alpha = 0.5f),
                    border = border(0.75.dp, ChampagneBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = matchResult.foundItem.title,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Medium,
                            color = MidnightNavy,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${matchResult.foundItem.campusLocation.displayName.uppercase()} • ${matchResult.foundItem.dateString}",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 11.sp,
                            letterSpacing = 0.8.sp,
                            color = EditorialTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "CRITERIOS DE COINCIDENCIA:",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = MidnightNavy
                )
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    matchResult.breakdowns.filter { it.pointsAwarded > 0 }.forEach { rule ->
                        Surface(
                            shape = RoundedCornerShape(3.dp),
                            color = EditorialSuccessContainer,
                            border = border(0.5.dp, EditorialSuccess.copy(alpha = 0.25f))
                        ) {
                            Text(
                                text = "✓ ${rule.ruleName}: +${rule.pointsAwarded} pts",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Medium,
                                color = EditorialSuccess,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onViewAndClaim,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MidnightNavy),
                modifier = Modifier.testTag("match_view_and_claim_button")
            ) {
                Text(
                    "VER Y SOLICITAR RECUPERACIÓN",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "CERRAR",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = EditorialTextSecondary
                )
            }
        }
    )
}

@Composable
fun ClaimRequestDialog(
    foundItemTitle: String,
    onDismiss: () -> Unit,
    onSubmitClaim: (studentAnswer: String) -> Unit
) {
    var studentAnswer by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(4.dp),
        containerColor = LuxurySurface,
        icon = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(ChampagneLight)
                    .border(0.75.dp, ChampagneBorder, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = ChampagneDark,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        title = {
            Text(
                text = "SOLICITAR RECUPERACIÓN",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                letterSpacing = 1.5.sp,
                color = MidnightNavy,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                Text(
                    text = "Para transferir '$foundItemTitle' a su dueño legítimo, la universidad protege los objetos mediante un detalle de control oculto.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        color = EditorialTextSecondary,
                        lineHeight = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = ChampagneLight,
                    border = border(0.75.dp, ChampagneBorder)
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = null,
                            tint = ChampagneDark,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Describe una marca privada, calcomanía, número de serie o característica oculta que no aparezca en la descripción pública.",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = ChampagneDark,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = studentAnswer,
                    onValueChange = {
                        studentAnswer = it
                        isError = false
                    },
                    label = { Text("Tu respuesta / Detalle privado", fontFamily = FontFamily.SansSerif) },
                    placeholder = { Text("Ej: Sticker azul pequeño en la esquina", fontFamily = FontFamily.SansSerif) },
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text("Por favor escribe una descripción para que el custodio la verifique.", fontFamily = FontFamily.SansSerif)
                        }
                    },
                    shape = RoundedCornerShape(4.dp),
                    minLines = 3,
                    maxLines = 4,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("claim_answer_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (studentAnswer.trim().length < 3) {
                        isError = true
                    } else {
                        onSubmitClaim(studentAnswer.trim())
                    }
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MidnightNavy),
                modifier = Modifier.testTag("submit_claim_confirm_button")
            ) {
                Text(
                    "ENVIAR SOLICITUD",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "CANCELAR",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = EditorialTextSecondary
                )
            }
        }
    )
}

@Composable
fun ItemSummaryCard(
    item: ReportedItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("item_card_${item.id}"),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = LuxurySurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = border(0.75.dp, LuxuryBorder)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemTypeBadge(type = item.type)
                StatusBadge(status = item.status)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = MidnightNavy
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = ChampagneDark,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${item.campusLocation.displayName.uppercase()} • ${item.dateString.uppercase()}",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 0.8.sp,
                    color = EditorialTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.publicDescription,
                fontSize = 13.sp,
                fontFamily = FontFamily.SansSerif,
                color = EditorialTextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 19.sp
            )

            if (item.privateControlDetail != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(3.dp),
                    color = ChampagneLight,
                    border = border(0.5.dp, ChampagneBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = ChampagneDark,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "VERIFICACIÓN DE CONTROL UAM",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.8.sp,
                            color = ChampagneDark
                        )
                    }
                }
            }
        }
    }
}


