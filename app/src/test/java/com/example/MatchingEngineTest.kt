package com.example

import com.example.data.model.CampusLocation
import com.example.data.model.ItemCategory
import com.example.data.model.ItemStatus
import com.example.data.model.ItemType
import com.example.data.model.ReportedItem
import com.example.domain.MatchingEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MatchingEngineTest {

    @Test
    fun `test presentation case matches with high score`() {
        // Objeto encontrado en Biblioteca
        val foundLaptop = ReportedItem(
            id = "found-1",
            type = ItemType.ENCONTRADO,
            title = "Laptop Lenovo gris",
            category = ItemCategory.ELECTRONICA,
            color = "Gris",
            brand = "Lenovo",
            campusLocation = CampusLocation.BIBLIOTECA,
            dateString = "Ayer",
            publicDescription = "Laptop Lenovo color gris encontrada en el segundo piso.",
            privateControlDetail = "Sticker azul de Python en la esquina derecha",
            reportedByName = "Seguridad Campus",
            reportedByEmail = "admin@uam.edu.ni",
            status = ItemStatus.ACTIVO
        )

        // Objeto perdido reportado por el estudiante
        val lostLaptop = ReportedItem(
            id = "lost-1",
            type = ItemType.PERDIDO,
            title = "Laptop Lenovo gris",
            category = ItemCategory.ELECTRONICA,
            color = "Gris",
            brand = "Lenovo",
            campusLocation = CampusLocation.BIBLIOTECA,
            dateString = "Ayer",
            publicDescription = "Olvidé mi laptop Lenovo gris cerca de los ventanales.",
            privateControlDetail = null,
            reportedByName = "Ana Martínez",
            reportedByEmail = "estudiante@uam.edu.ni",
            status = ItemStatus.ACTIVO
        )

        val match = MatchingEngine.calculateMatch(lostLaptop, foundLaptop)

        // Misma categoría (+25), Mismo lugar (+25), Mismo color (+15), Misma marca (+15), Fecha (+10), Palabras clave (+10) = 100
        assertTrue("El score debe ser mayor o igual a 85", match.score >= 85)
        assertTrue("Debe considerarse coincidencia alta", match.isHighMatch)
    }

    @Test
    fun `test different categories receive 0 points`() {
        val foundKey = ReportedItem(
            id = "found-2",
            type = ItemType.ENCONTRADO,
            title = "Llavero metálico",
            category = ItemCategory.LLAVES,
            color = "Plateado",
            brand = "",
            campusLocation = CampusLocation.CAFETERIA,
            dateString = "Hoy",
            publicDescription = "Llaves encontradas.",
            privateControlDetail = "3 llaves con cinta verde",
            reportedByName = "Admin",
            reportedByEmail = "admin@uam.edu.ni",
            status = ItemStatus.ACTIVO
        )

        val lostBook = ReportedItem(
            id = "lost-2",
            type = ItemType.PERDIDO,
            title = "Libro de Cálculo",
            category = ItemCategory.OTROS,
            color = "Azul",
            brand = "Pearson",
            campusLocation = CampusLocation.AULAS_EDIFICIO_A,
            dateString = "Hoy",
            publicDescription = "Libro extraviado.",
            privateControlDetail = null,
            reportedByName = "Ana",
            reportedByEmail = "estudiante@uam.edu.ni",
            status = ItemStatus.ACTIVO
        )

        val match = MatchingEngine.calculateMatch(lostBook, foundKey)
        // Solo coincide la fecha (10 pts), por lo que no es una coincidencia alta (< 50 pts)
        assertEquals(10, match.score)
        org.junit.Assert.assertFalse("No debe ser coincidencia alta", match.isHighMatch)
    }
}
