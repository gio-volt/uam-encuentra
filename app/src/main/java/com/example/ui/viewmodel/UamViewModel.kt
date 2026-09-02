package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.CampusLocation
import com.example.data.model.ClaimRequest
import com.example.data.model.ClaimStatus
import com.example.data.model.ItemCategory
import com.example.data.model.ItemStatus
import com.example.data.model.ItemType
import com.example.data.model.MatchResult
import com.example.data.model.ReportedItem
import com.example.data.model.UserRole
import com.example.data.repository.UamMockData
import com.example.domain.MatchingEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

sealed class AppDestination {
    object Home : AppDestination()
    object BrowseFound : AppDestination()
    data class ReportForm(val type: ItemType) : AppDestination()
    object AdminPanel : AppDestination()
    object MyReports : AppDestination()
    data class ItemDetail(val itemId: String) : AppDestination()
}

data class AdminMetrics(
    val totalCount: Int,
    val lostCount: Int,
    val foundCount: Int,
    val highMatchesCount: Int,
    val pendingClaimsCount: Int,
    val recoveredCount: Int
)

class UamViewModel : ViewModel() {

    private val _currentRole = MutableStateFlow(UserRole.ESTUDIANTE)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    private val _items = MutableStateFlow<List<ReportedItem>>(UamMockData.getInitialItems())
    val items: StateFlow<List<ReportedItem>> = _items.asStateFlow()

    private val _claims = MutableStateFlow<List<ClaimRequest>>(UamMockData.getInitialClaims())
    val claims: StateFlow<List<ClaimRequest>> = _claims.asStateFlow()

    private val _currentDestination = MutableStateFlow<AppDestination>(AppDestination.Home)
    val currentDestination: StateFlow<AppDestination> = _currentDestination.asStateFlow()

    // Alert for immediate high match on reporting lost item
    private val _immediateMatchResult = MutableStateFlow<MatchResult?>(null)
    val immediateMatchResult: StateFlow<MatchResult?> = _immediateMatchResult.asStateFlow()

    // Snackbar / Feedback message
    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    fun switchRole(role: UserRole) {
        _currentRole.value = role
        _userMessage.value = "Sesión cambiada a: ${role.displayName} (${role.roleTitle})"
    }

    fun navigateTo(destination: AppDestination) {
        _currentDestination.value = destination
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }

    fun dismissImmediateMatch() {
        _immediateMatchResult.value = null
    }

    fun resetDemoData() {
        _items.value = UamMockData.getInitialItems()
        _claims.value = UamMockData.getInitialClaims()
        _immediateMatchResult.value = null
        _currentDestination.value = AppDestination.Home
        _userMessage.value = "Datos demo reiniciados al estado original UAM"
    }

    fun reportLostItem(
        title: String,
        category: ItemCategory,
        color: String,
        brand: String,
        campusLocation: CampusLocation,
        dateString: String,
        description: String
    ): ReportedItem {
        val currentUser = _currentRole.value
        val newItem = ReportedItem(
            id = "lost-uam-${UUID.randomUUID().toString().take(6)}",
            type = ItemType.PERDIDO,
            title = title.trim(),
            category = category,
            color = color.trim(),
            brand = brand.trim(),
            campusLocation = campusLocation,
            dateString = dateString.trim().ifEmpty { "Hoy" },
            timestamp = System.currentTimeMillis(),
            publicDescription = description.trim(),
            privateControlDetail = null,
            reportedByName = currentUser.displayName,
            reportedByEmail = currentUser.email,
            status = ItemStatus.ACTIVO
        )

        _items.update { listOf(newItem) + it }

        // Ejecutar motor de coincidencias contra todos los objetos encontrados activos
        val activeFound = _items.value.filter {
            it.type == ItemType.ENCONTRADO && it.status != ItemStatus.RECUPERADO
        }

        var bestMatch: MatchResult? = null
        for (found in activeFound) {
            val result = MatchingEngine.calculateMatch(newItem, found)
            if (result.score >= 70) {
                if (bestMatch == null || result.score > bestMatch.score) {
                    bestMatch = result
                }
            }
        }

        if (bestMatch != null && bestMatch.score >= 70) {
            _immediateMatchResult.value = bestMatch
        } else {
            _userMessage.value = "Reporte de extravío registrado con éxito."
        }

        return newItem
    }

    fun reportFoundItem(
        title: String,
        category: ItemCategory,
        color: String,
        brand: String,
        campusLocation: CampusLocation,
        dateString: String,
        description: String,
        privateControlDetail: String
    ): ReportedItem {
        val currentUser = _currentRole.value
        val newItem = ReportedItem(
            id = "found-uam-${UUID.randomUUID().toString().take(6)}",
            type = ItemType.ENCONTRADO,
            title = title.trim(),
            category = category,
            color = color.trim(),
            brand = brand.trim(),
            campusLocation = campusLocation,
            dateString = dateString.trim().ifEmpty { "Hoy" },
            timestamp = System.currentTimeMillis(),
            publicDescription = description.trim(),
            privateControlDetail = privateControlDetail.trim(),
            reportedByName = currentUser.displayName,
            reportedByEmail = currentUser.email,
            status = ItemStatus.ACTIVO
        )

        _items.update { listOf(newItem) + it }
        _userMessage.value = "Objeto encontrado registrado en custodia UAM. El detalle de control fue guardado confidencialmente."
        return newItem
    }

    fun submitClaimRequest(
        foundItemId: String,
        studentAnswer: String,
        lostItemId: String? = null
    ) {
        val currentUser = _currentRole.value
        val claimId = "claim-uam-${UUID.randomUUID().toString().take(6)}"

        val newClaim = ClaimRequest(
            id = claimId,
            foundItemId = foundItemId,
            lostItemId = lostItemId,
            studentName = currentUser.displayName,
            studentEmail = currentUser.email,
            studentAnswerPrivateDetail = studentAnswer.trim(),
            status = ClaimStatus.PENDIENTE,
            requestDateString = "Hoy",
            timestamp = System.currentTimeMillis()
        )

        _claims.update { listOf(newClaim) + it }

        // Actualizar el estado del item encontrado a EN_REVISION
        _items.update { currentList ->
            currentList.map { item ->
                if (item.id == foundItemId) {
                    item.copy(
                        status = ItemStatus.EN_REVISION,
                        activeClaimId = claimId
                    )
                } else {
                    item
                }
            }
        }

        _userMessage.value = "¡Solicitud enviada con éxito! El administrador verificará tu respuesta de control."
    }

    fun approveClaim(claimId: String, notes: String = "Verificación exitosa por el administrador UAM.") {
        val targetClaim = _claims.value.find { it.id == claimId } ?: return

        // Actualizar solicitud
        _claims.update { list ->
            list.map {
                if (it.id == claimId) {
                    it.copy(
                        status = ClaimStatus.APROBADA,
                        resolutionNotes = notes
                    )
                } else it
            }
        }

        // Actualizar objeto encontrado a RECUPERADO
        _items.update { list ->
            list.map { item ->
                if (item.id == targetClaim.foundItemId) {
                    item.copy(status = ItemStatus.RECUPERADO)
                } else if (targetClaim.lostItemId != null && item.id == targetClaim.lostItemId) {
                    item.copy(status = ItemStatus.RECUPERADO)
                } else {
                    item
                }
            }
        }

        _userMessage.value = "Solicitud aprobada: El objeto ha sido marcado como RECUPERADO y entregado."
    }

    fun rejectClaim(claimId: String, notes: String = "La respuesta no coincide con el detalle privado registrado.") {
        val targetClaim = _claims.value.find { it.id == claimId } ?: return

        _claims.update { list ->
            list.map {
                if (it.id == claimId) {
                    it.copy(
                        status = ClaimStatus.RECHAZADA,
                        resolutionNotes = notes
                    )
                } else it
            }
        }

        // Devolver objeto a estado ACTIVO si no hay más solicitudes pendientes
        _items.update { list ->
            list.map { item ->
                if (item.id == targetClaim.foundItemId) {
                    item.copy(status = ItemStatus.ACTIVO, activeClaimId = null)
                } else item
            }
        }

        _userMessage.value = "Solicitud rechazada. El objeto vuelve a estar en custodia activa."
    }

    fun findItemById(id: String): ReportedItem? {
        return _items.value.find { it.id == id }
    }

    fun getClaimForFoundItem(foundItemId: String): ClaimRequest? {
        return _claims.value.find { it.foundItemId == foundItemId && it.status == ClaimStatus.PENDIENTE }
            ?: _claims.value.find { it.foundItemId == foundItemId }
    }

    fun getMatchesForLostItem(lostItem: ReportedItem): List<MatchResult> {
        val foundItems = _items.value.filter {
            it.type == ItemType.ENCONTRADO && it.status != ItemStatus.RECUPERADO
        }
        return foundItems.map { MatchingEngine.calculateMatch(lostItem, it) }
            .sortedByDescending { it.score }
    }

    fun calculateAdminMetrics(): AdminMetrics {
        val allItems = _items.value
        val allClaims = _claims.value

        val total = allItems.size
        val lostCount = allItems.count { it.type == ItemType.PERDIDO && it.status != ItemStatus.RECUPERADO }
        val foundCount = allItems.count { it.type == ItemType.ENCONTRADO && it.status != ItemStatus.RECUPERADO }
        val recoveredCount = allItems.count { it.status == ItemStatus.RECUPERADO }
        val pendingClaims = allClaims.count { it.status == ClaimStatus.PENDIENTE }

        // Calcular conteo de coincidencias altas activas
        val activeLost = allItems.filter { it.type == ItemType.PERDIDO && it.status == ItemStatus.ACTIVO }
        val activeFound = allItems.filter { it.type == ItemType.ENCONTRADO && it.status == ItemStatus.ACTIVO }
        var highMatches = 0

        for (lost in activeLost) {
            val hasHighMatch = activeFound.any { found ->
                MatchingEngine.calculateMatch(lost, found).isHighMatch
            }
            if (hasHighMatch) highMatches++
        }

        return AdminMetrics(
            totalCount = total,
            lostCount = lostCount,
            foundCount = foundCount,
            highMatchesCount = highMatches,
            pendingClaimsCount = pendingClaims,
            recoveredCount = recoveredCount
        )
    }
}
