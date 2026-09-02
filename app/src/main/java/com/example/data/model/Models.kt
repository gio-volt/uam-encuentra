package com.example.data.model

enum class UserRole(
    val displayName: String,
    val email: String,
    val roleTitle: String
) {
    ESTUDIANTE(
        displayName = "Ana Martínez",
        email = "estudiante@uam.edu.ni",
        roleTitle = "Estudiante UAM"
    ),
    ADMINISTRADOR(
        displayName = "Admin UAM",
        email = "admin@uam.edu.ni",
        roleTitle = "Seguridad y Custodia UAM"
    )
}

enum class ItemType(val label: String) {
    PERDIDO("Objeto Perdido"),
    ENCONTRADO("Objeto Encontrado")
}

enum class ItemCategory(val displayName: String, val iconName: String) {
    ELECTRONICA("Electrónica", "devices"),
    DOCUMENTOS("Documentos y Carnets", "badge"),
    LLAVES("Llaves", "vpn_key"),
    ROPA("Ropa y Calzado", "checkroom"),
    MOCHILAS("Mochilas y Billeteras", "backpack"),
    ACCESORIOS("Accesorios y Joyería", "watch"),
    TERMOS("Termos y Botellas", "water_drop"),
    OTROS("Otros Artículos", "category")
}

enum class CampusLocation(val displayName: String, val zone: String) {
    BIBLIOTECA("Biblioteca Central", "Piso 1 y 2"),
    CAFETERIA("Cafetería Central", "Área de Comedores"),
    AULAS_EDIFICIO_A("Aulas Edificio A", "Facultad de Ingeniería y Negocios"),
    AULAS_EDIFICIO_B("Aulas Edificio B", "Facultad de Medicina y Odontología"),
    LABORATORIO_COMPUTO("Laboratorio JaguarLab", "Edificio de Cómputo"),
    CANCHAS_POLIDEPORTIVAS("Canchas Polideportivas", "Área Deportiva UAM"),
    AUDITORIO("Auditorio Central UAM", "Planta Baja"),
    ESTACIONAMIENTO("Estacionamiento Principal", "Zona de Parqueos"),
    GIMNASIO("Gimnasio UAM", "Complejo Deportivo"),
    CLINICA("Clínica Médica UAM", "Salud Estudiantil")
}

enum class ItemStatus(val label: String) {
    ACTIVO("En Custodia / Reportado"),
    EN_REVISION("En Revisión de Reclamo"),
    RECUPERADO("Recuperado con Éxito")
}

enum class ClaimStatus(val label: String) {
    PENDIENTE("Pendiente de Aprobación"),
    APROBADA("Aprobada y Entregada"),
    RECHAZADA("Rechazada")
}

data class ReportedItem(
    val id: String,
    val type: ItemType,
    val title: String,
    val category: ItemCategory,
    val color: String,
    val brand: String,
    val campusLocation: CampusLocation,
    val dateString: String,
    val timestamp: Long = System.currentTimeMillis(),
    val publicDescription: String,
    val privateControlDetail: String? = null, // Solo para objetos encontrados. NUNCA se muestra públicamente
    val reportedByName: String,
    val reportedByEmail: String,
    val status: ItemStatus = ItemStatus.ACTIVO,
    val activeClaimId: String? = null
)

data class ClaimRequest(
    val id: String,
    val foundItemId: String,
    val lostItemId: String? = null,
    val studentName: String,
    val studentEmail: String,
    val studentAnswerPrivateDetail: String,
    val status: ClaimStatus = ClaimStatus.PENDIENTE,
    val requestDateString: String,
    val timestamp: Long = System.currentTimeMillis(),
    val resolutionNotes: String? = null
)

data class MatchBreakdown(
    val ruleName: String,
    val pointsAwarded: Int,
    val maxPoints: Int,
    val explanation: String
)

data class MatchResult(
    val lostItem: ReportedItem,
    val foundItem: ReportedItem,
    val score: Int, // 0 to 100
    val breakdowns: List<MatchBreakdown>,
    val isHighMatch: Boolean // score >= 80
) {
    val scorePercentage: String get() = "$score%"
}
