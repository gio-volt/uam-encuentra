package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.ClaimStatus
import com.example.data.model.ItemType
import com.example.data.model.UserRole
import com.example.ui.components.HighMatchAlertDialog
import com.example.ui.components.UamTopAppBar
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ItemDetailScreen
import com.example.ui.screens.ItemsListScreen
import com.example.ui.screens.MyReportsScreen
import com.example.ui.screens.ReportItemScreen
import com.example.ui.theme.LuxuryBorder
import com.example.ui.theme.LuxurySurface
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.UamBlue
import com.example.ui.theme.UamGold
import com.example.ui.theme.UamNavy
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.UamViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                UamApp()
            }
        }
    }
}

@Composable
fun UamApp(viewModel: UamViewModel = viewModel()) {
    val currentRole by viewModel.currentRole.collectAsState()
    val items by viewModel.items.collectAsState()
    val claims by viewModel.claims.collectAsState()
    val currentDestination by viewModel.currentDestination.collectAsState()
    val immediateMatchResult by viewModel.immediateMatchResult.collectAsState()
    val userMessage by viewModel.userMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val metrics = viewModel.calculateAdminMetrics()

    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearUserMessage()
        }
    }

    val canNavigateBack = currentDestination !is AppDestination.Home

    BackHandler(enabled = canNavigateBack) {
        viewModel.navigateTo(AppDestination.Home)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            UamTopAppBar(
                currentRole = currentRole,
                onRoleChange = { role ->
                    viewModel.switchRole(role)
                    if (role == UserRole.ADMINISTRADOR && currentDestination is AppDestination.MyReports) {
                        viewModel.navigateTo(AppDestination.AdminPanel)
                    }
                },
                onResetDemo = { viewModel.resetDemoData() },
                canNavigateBack = canNavigateBack,
                onNavigateBack = { viewModel.navigateTo(AppDestination.Home) }
            )
        },
        bottomBar = {
            Surface(
                color = LuxurySurface,
                border = androidx.compose.foundation.BorderStroke(0.75.dp, LuxuryBorder)
            ) {
                NavigationBar(
                    containerColor = LuxurySurface,
                    tonalElevation = 0.dp
                ) {
                    // Tab 1: Inicio
                    NavigationBarItem(
                        selected = currentDestination is AppDestination.Home,
                        onClick = { viewModel.navigateTo(AppDestination.Home) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = {
                            Text(
                                "INICIO",
                                fontSize = 9.5.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MidnightNavy,
                            selectedTextColor = MidnightNavy,
                            unselectedIconColor = com.example.ui.theme.EditorialTextMuted,
                            unselectedTextColor = com.example.ui.theme.EditorialTextMuted,
                            indicatorColor = com.example.ui.theme.ChampagneLight
                        ),
                        modifier = Modifier.testTag("tab_home")
                    )

                    // Tab 2: Encontrados
                    NavigationBarItem(
                        selected = currentDestination is AppDestination.BrowseFound,
                        onClick = { viewModel.navigateTo(AppDestination.BrowseFound) },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (metrics.foundCount > 0) {
                                        Badge(
                                            containerColor = MidnightNavy,
                                            contentColor = Color.White
                                        ) {
                                            Text("${metrics.foundCount}")
                                        }
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Inventory2, contentDescription = "Encontrados")
                            }
                        },
                        label = {
                            Text(
                                "CUSTODIA",
                                fontSize = 9.5.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MidnightNavy,
                            selectedTextColor = MidnightNavy,
                            unselectedIconColor = com.example.ui.theme.EditorialTextMuted,
                            unselectedTextColor = com.example.ui.theme.EditorialTextMuted,
                            indicatorColor = com.example.ui.theme.ChampagneLight
                        ),
                        modifier = Modifier.testTag("tab_browse_found")
                    )

                    // Tab 3: Reportar
                    NavigationBarItem(
                        selected = currentDestination is AppDestination.ReportForm,
                        onClick = { viewModel.navigateTo(AppDestination.ReportForm(ItemType.PERDIDO)) },
                        icon = { Icon(Icons.Default.AddCircle, contentDescription = "Reportar") },
                        label = {
                            Text(
                                "REPORTAR",
                                fontSize = 9.5.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MidnightNavy,
                            selectedTextColor = MidnightNavy,
                            unselectedIconColor = com.example.ui.theme.EditorialTextMuted,
                            unselectedTextColor = com.example.ui.theme.EditorialTextMuted,
                            indicatorColor = com.example.ui.theme.ChampagneLight
                        ),
                        modifier = Modifier.testTag("tab_report")
                    )

                    // Tab 4: Panel Admin o Mis Reportes
                    if (currentRole == UserRole.ADMINISTRADOR) {
                        val pendingCount = claims.count { it.status == ClaimStatus.PENDIENTE }
                        NavigationBarItem(
                            selected = currentDestination is AppDestination.AdminPanel,
                            onClick = { viewModel.navigateTo(AppDestination.AdminPanel) },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (pendingCount > 0) {
                                            Badge(
                                                containerColor = com.example.ui.theme.EditorialError,
                                                contentColor = Color.White
                                            ) {
                                                Text("$pendingCount")
                                            }
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.AdminPanelSettings, contentDescription = "Panel Admin")
                                }
                            },
                            label = {
                                Text(
                                    "ADMIN",
                                    fontSize = 9.5.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MidnightNavy,
                                selectedTextColor = MidnightNavy,
                                unselectedIconColor = com.example.ui.theme.EditorialTextMuted,
                                unselectedTextColor = com.example.ui.theme.EditorialTextMuted,
                                indicatorColor = com.example.ui.theme.ChampagneLight
                            ),
                            modifier = Modifier.testTag("tab_admin_panel")
                        )
                    } else {
                        val myClaimsCount = claims.count { it.studentEmail == currentRole.email }
                        NavigationBarItem(
                            selected = currentDestination is AppDestination.MyReports,
                            onClick = { viewModel.navigateTo(AppDestination.MyReports) },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (myClaimsCount > 0) {
                                            Badge(
                                                containerColor = com.example.ui.theme.ChampagneDark,
                                                contentColor = Color.White
                                            ) {
                                                Text("$myClaimsCount")
                                            }
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Assignment, contentDescription = "Mis Reclamos")
                                }
                            },
                            label = {
                                Text(
                                    "RECLAMOS",
                                    fontSize = 9.5.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MidnightNavy,
                                selectedTextColor = MidnightNavy,
                                unselectedIconColor = com.example.ui.theme.EditorialTextMuted,
                                unselectedTextColor = com.example.ui.theme.EditorialTextMuted,
                                indicatorColor = com.example.ui.theme.ChampagneLight
                            ),
                            modifier = Modifier.testTag("tab_my_reports")
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val dest = currentDestination) {
                is AppDestination.Home -> {
                    HomeScreen(
                        currentRole = currentRole,
                        items = items,
                        metrics = metrics,
                        onNavigateToLostForm = { viewModel.navigateTo(AppDestination.ReportForm(ItemType.PERDIDO)) },
                        onNavigateToFoundForm = { viewModel.navigateTo(AppDestination.ReportForm(ItemType.ENCONTRADO)) },
                        onNavigateToBrowseFound = { viewModel.navigateTo(AppDestination.BrowseFound) },
                        onNavigateToAdmin = { viewModel.navigateTo(AppDestination.AdminPanel) },
                        onSelectItem = { item -> viewModel.navigateTo(AppDestination.ItemDetail(item.id)) }
                    )
                }

                is AppDestination.BrowseFound -> {
                    ItemsListScreen(
                        items = items,
                        onSelectItem = { item -> viewModel.navigateTo(AppDestination.ItemDetail(item.id)) }
                    )
                }

                is AppDestination.ReportForm -> {
                    ReportItemScreen(
                        itemType = dest.type,
                        onNavigateBack = { viewModel.navigateTo(AppDestination.Home) },
                        onSubmitLost = { title, category, color, brand, location, date, desc ->
                            viewModel.reportLostItem(
                                title = title,
                                category = category,
                                color = color,
                                brand = brand,
                                campusLocation = location,
                                dateString = date,
                                description = desc
                            )
                        },
                        onSubmitFound = { title, category, color, brand, location, date, desc, privateDetail ->
                            viewModel.reportFoundItem(
                                title = title,
                                category = category,
                                color = color,
                                brand = brand,
                                campusLocation = location,
                                dateString = date,
                                description = desc,
                                privateControlDetail = privateDetail
                            )
                            viewModel.navigateTo(AppDestination.BrowseFound)
                        }
                    )
                }

                is AppDestination.AdminPanel -> {
                    AdminPanelScreen(
                        metrics = metrics,
                        claims = claims,
                        items = items,
                        onApproveClaim = { claimId -> viewModel.approveClaim(claimId) },
                        onRejectClaim = { claimId -> viewModel.rejectClaim(claimId) },
                        onSelectItem = { item -> viewModel.navigateTo(AppDestination.ItemDetail(item.id)) }
                    )
                }

                is AppDestination.MyReports -> {
                    val myItems = items.filter { it.reportedByEmail == currentRole.email }
                    val myClaims = claims.filter { it.studentEmail == currentRole.email }
                    MyReportsScreen(
                        currentRole = currentRole,
                        myItems = myItems,
                        myClaims = myClaims,
                        allItems = items,
                        onSelectItem = { item -> viewModel.navigateTo(AppDestination.ItemDetail(item.id)) },
                        onNavigateToLostForm = { viewModel.navigateTo(AppDestination.ReportForm(ItemType.PERDIDO)) }
                    )
                }

                is AppDestination.ItemDetail -> {
                    val currentItem = viewModel.findItemById(dest.itemId)
                    if (currentItem != null) {
                        val activeClaim = viewModel.getClaimForFoundItem(currentItem.id)
                        val matches = if (currentItem.type == ItemType.PERDIDO) {
                            viewModel.getMatchesForLostItem(currentItem)
                        } else emptyList()

                        ItemDetailScreen(
                            item = currentItem,
                            currentRole = currentRole,
                            activeClaim = activeClaim,
                            matchesForLost = matches,
                            onNavigateBack = { viewModel.navigateTo(AppDestination.Home) },
                            onSubmitClaim = { answer ->
                                viewModel.submitClaimRequest(
                                    foundItemId = currentItem.id,
                                    studentAnswer = answer
                                )
                            },
                            onApproveClaim = { claimId -> viewModel.approveClaim(claimId) },
                            onRejectClaim = { claimId -> viewModel.rejectClaim(claimId) },
                            onSelectMatchingItem = { matchedItem ->
                                viewModel.navigateTo(AppDestination.ItemDetail(matchedItem.id))
                            }
                        )
                    } else {
                        viewModel.navigateTo(AppDestination.Home)
                    }
                }
            }
        }
    }

    // Modal de Alerta Inmediata cuando se detecta alta coincidencia tras reportar un objeto perdido
    immediateMatchResult?.let { match ->
        HighMatchAlertDialog(
            matchResult = match,
            onDismiss = { viewModel.dismissImmediateMatch() },
            onViewAndClaim = {
                viewModel.dismissImmediateMatch()
                viewModel.navigateTo(AppDestination.ItemDetail(match.foundItem.id))
            }
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme { Greeting("Android") }
}
