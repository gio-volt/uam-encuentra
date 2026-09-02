package com.example.data.repository

import com.example.data.model.CampusLocation
import com.example.data.model.ClaimRequest
import com.example.data.model.ClaimStatus
import com.example.data.model.ItemCategory
import com.example.data.model.ItemStatus
import com.example.data.model.ItemType
import com.example.data.model.ReportedItem

object UamMockData {

    const val DEMO_FOUND_LAPTOP_ID = "found-uam-101"

    fun getInitialItems(): List<ReportedItem> {
        val now = System.currentTimeMillis()
        val oneDayMillis = 24L * 60 * 60 * 1000

        return listOf(
            // CASO OBLIGATORIO DE PRESENTACIÓN
            ReportedItem(
                id = DEMO_FOUND_LAPTOP_ID,
                type = ItemType.ENCONTRADO,
                title = "Laptop Lenovo gris",
                category = ItemCategory.ELECTRONICA,
                color = "Gris",
                brand = "Lenovo",
                campusLocation = CampusLocation.BIBLIOTECA,
                dateString = "Ayer",
                timestamp = now - (oneDayMillis * 1),
                publicDescription = "Laptop encontrada en el segundo piso de la Biblioteca Central sobre una de las mesas individuales de estudio. Se encuentra en resguardo en recepción de biblioteca.",
                privateControlDetail = "Sticker azul pequeño", // DETALLE PRIVADO DE CONTROL
                reportedByName = "Custodia Biblioteca Central",
                reportedByEmail = "biblioteca@uam.edu.ni",
                status = ItemStatus.ACTIVO
            ),
            ReportedItem(
                id = "found-uam-102",
                type = ItemType.ENCONTRADO,
                title = "Billetera de cuero marrón",
                category = ItemCategory.MOCHILAS,
                color = "Marrón",
                brand = "Tommy Hilfiger",
                campusLocation = CampusLocation.CAFETERIA,
                dateString = "Hace 2 días",
                timestamp = now - (oneDayMillis * 2),
                publicDescription = "Entregada por el personal de limpieza de la cafetería central. Guardada en caja de seguridad del edificio administrativo.",
                privateControlDetail = "Credencial del gimnasio UAM y ticket de parqueo dentro",
                reportedByName = "Seguridad UAM",
                reportedByEmail = "seguridad@uam.edu.ni",
                status = ItemStatus.ACTIVO
            ),
            ReportedItem(
                id = "found-uam-103",
                type = ItemType.ENCONTRADO,
                title = "Carnet Estudiantil UAM",
                category = ItemCategory.DOCUMENTOS,
                color = "Azul y Blanco",
                brand = "Universidad Americana",
                campusLocation = CampusLocation.AULAS_EDIFICIO_A,
                dateString = "Hoy por la mañana",
                timestamp = now - (oneDayMillis / 4),
                publicDescription = "Encontrado en pasillo de planta baja de Edificio A, cerca del aula A-104.",
                privateControlDetail = "Calcomanía de Facultad de Marketing en el reverso con firma borrosa",
                reportedByName = "Personal Docente",
                reportedByEmail = "docentes@uam.edu.ni",
                status = ItemStatus.ACTIVO
            ),
            ReportedItem(
                id = "found-uam-104",
                type = ItemType.ENCONTRADO,
                title = "Llavero Jaguar UAM con 3 llaves",
                category = ItemCategory.LLAVES,
                color = "Dorado y Negro",
                brand = "UAM Jaguares",
                campusLocation = CampusLocation.CANCHAS_POLIDEPORTIVAS,
                dateString = "Hace 3 días",
                timestamp = now - (oneDayMillis * 3),
                publicDescription = "Dejado en las gradas de la cancha principal de baloncesto.",
                privateControlDetail = "Tiene un destapador metálico miniatura y cinta adhesiva en una llave",
                reportedByName = "Coordinador Deportivo",
                reportedByEmail = "deportes@uam.edu.ni",
                status = ItemStatus.ACTIVO
            ),
            ReportedItem(
                id = "found-uam-105",
                type = ItemType.ENCONTRADO,
                title = "Termo metálico Hydro Flask",
                category = ItemCategory.TERMOS,
                color = "Negro mate",
                brand = "Hydro Flask",
                campusLocation = CampusLocation.GIMNASIO,
                dateString = "Hace 4 días",
                timestamp = now - (oneDayMillis * 4),
                publicDescription = "Localizado en el rack de mancuernas del gimnasio universitario.",
                privateControlDetail = "Golpe visible en la base y tapa con boquilla morada",
                reportedByName = "Recepción Gimnasio",
                reportedByEmail = "gimnasio@uam.edu.ni",
                status = ItemStatus.ACTIVO
            ),
            ReportedItem(
                id = "lost-uam-201",
                type = ItemType.PERDIDO,
                title = "Audífonos inalámbricos Sony WH-1000XM4",
                category = ItemCategory.ELECTRONICA,
                color = "Negro",
                brand = "Sony",
                campusLocation = CampusLocation.LABORATORIO_COMPUTO,
                dateString = "Ayer",
                timestamp = now - (oneDayMillis * 1),
                publicDescription = "Olvidé mis audífonos de diadema en el laboratorio JaguarLab en la estación 12.",
                reportedByName = "Carlos Mendoza",
                reportedByEmail = "carlos.mendoza@uam.edu.ni",
                status = ItemStatus.ACTIVO
            ),
            ReportedItem(
                id = "recovered-uam-301",
                type = ItemType.ENCONTRADO,
                title = "Calculadora Científica Casio FX-991",
                category = ItemCategory.ELECTRONICA,
                color = "Negro y Gris",
                brand = "Casio",
                campusLocation = CampusLocation.AULAS_EDIFICIO_B,
                dateString = "La semana pasada",
                timestamp = now - (oneDayMillis * 7),
                publicDescription = "Calculadora olvidada en examen de cálculo integral en aula B-201.",
                privateControlDetail = "Nombre 'Luis R.' escrito con corrector en el estuche",
                reportedByName = "Profesor de Matemáticas",
                reportedByEmail = "matematicas@uam.edu.ni",
                status = ItemStatus.RECUPERADO
            )
        )
    }

    fun getInitialClaims(): List<ClaimRequest> {
        val now = System.currentTimeMillis()
        val oneDayMillis = 24L * 60 * 60 * 1000

        return listOf(
            ClaimRequest(
                id = "claim-hist-1",
                foundItemId = "recovered-uam-301",
                studentName = "Luis Rodríguez",
                studentEmail = "luis.rodriguez@uam.edu.ni",
                studentAnswerPrivateDetail = "Tiene mi nombre 'Luis R.' escrito con marcador/corrector blanco en la tapa protectora",
                status = ClaimStatus.APROBADA,
                requestDateString = "Hace 5 días",
                timestamp = now - (oneDayMillis * 5),
                resolutionNotes = "Verificado en recepción. Coincide exactamente con el estuche."
            )
        )
    }
}
