package com.example.domain

import com.example.data.model.MatchBreakdown
import com.example.data.model.MatchResult
import com.example.data.model.ReportedItem
import java.text.Normalizer
import kotlin.math.abs

object MatchingEngine {

    /**
     * Algoritmo de puntuación UAM Encuentra (0 a 100%):
     * - Misma categoría: +25 pts
     * - Mismo lugar: +25 pts
     * - Mismo color: +15 pts
     * - Misma marca: +15 pts
     * - Fecha cercana: +10 pts
     * - Palabras clave coincidentes: +10 pts
     * Si supera 80%, clasifica como Coincidencia Alta.
     */
    fun calculateMatch(lostItem: ReportedItem, foundItem: ReportedItem): MatchResult {
        val breakdowns = mutableListOf<MatchBreakdown>()
        var totalScore = 0

        // 1. Misma Categoría (+25 pts)
        if (lostItem.category == foundItem.category) {
            totalScore += 25
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Categoría",
                    pointsAwarded = 25,
                    maxPoints = 25,
                    explanation = "Misma categoría: ${lostItem.category.displayName}"
                )
            )
        } else {
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Categoría",
                    pointsAwarded = 0,
                    maxPoints = 25,
                    explanation = "Categorías distintas (${lostItem.category.displayName} vs ${foundItem.category.displayName})"
                )
            )
        }

        // 2. Mismo Lugar del Campus (+25 pts)
        if (lostItem.campusLocation == foundItem.campusLocation) {
            totalScore += 25
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Ubicación en Campus",
                    pointsAwarded = 25,
                    maxPoints = 25,
                    explanation = "Mismo lugar reportado: ${lostItem.campusLocation.displayName}"
                )
            )
        } else {
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Ubicación en Campus",
                    pointsAwarded = 0,
                    maxPoints = 25,
                    explanation = "Lugares diferentes (${lostItem.campusLocation.displayName} vs ${foundItem.campusLocation.displayName})"
                )
            )
        }

        // 3. Mismo Color (+15 pts)
        val lostColorNorm = normalizeString(lostItem.color)
        val foundColorNorm = normalizeString(foundItem.color)
        val colorMatches = lostColorNorm.isNotEmpty() && foundColorNorm.isNotEmpty() &&
                (lostColorNorm.contains(foundColorNorm) || foundColorNorm.contains(lostColorNorm) ||
                        isColorSynonym(lostColorNorm, foundColorNorm))

        if (colorMatches) {
            totalScore += 15
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Color",
                    pointsAwarded = 15,
                    maxPoints = 15,
                    explanation = "Mismo color o tonalidad: ${foundItem.color}"
                )
            )
        } else {
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Color",
                    pointsAwarded = 0,
                    maxPoints = 15,
                    explanation = "Colores no coincidentes (${lostItem.color} vs ${foundItem.color})"
                )
            )
        }

        // 4. Misma Marca (+15 pts)
        val lostBrandNorm = normalizeString(lostItem.brand)
        val foundBrandNorm = normalizeString(foundItem.brand)
        val brandMatches = lostBrandNorm.isNotEmpty() && foundBrandNorm.isNotEmpty() &&
                (lostBrandNorm.contains(foundBrandNorm) || foundBrandNorm.contains(lostBrandNorm))

        if (brandMatches) {
            totalScore += 15
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Marca",
                    pointsAwarded = 15,
                    maxPoints = 15,
                    explanation = "Misma marca: ${foundItem.brand}"
                )
            )
        } else {
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Marca",
                    pointsAwarded = 0,
                    maxPoints = 15,
                    explanation = "Marcas no coincidentes (${lostItem.brand} vs ${foundItem.brand})"
                )
            )
        }

        // 5. Fecha Cercana (+10 pts)
        // Si fueron reportados con diferencia menor a 7 días, o misma fecha
        val timeDiffMillis = abs(lostItem.timestamp - foundItem.timestamp)
        val daysDiff = timeDiffMillis / (1000 * 60 * 60 * 24)
        val dateMatches = daysDiff <= 7 ||
                normalizeString(lostItem.dateString) == normalizeString(foundItem.dateString) ||
                lostItem.dateString.contains("Ayer", ignoreCase = true) ||
                lostItem.dateString.contains("Hoy", ignoreCase = true)

        if (dateMatches) {
            totalScore += 10
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Rango de Fecha",
                    pointsAwarded = 10,
                    maxPoints = 10,
                    explanation = "Fechas muy próximas o consistentes"
                )
            )
        } else {
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Rango de Fecha",
                    pointsAwarded = 0,
                    maxPoints = 10,
                    explanation = "Fechas con amplia diferencia temporal"
                )
            )
        }

        // 6. Palabras Clave Coincidentes (+10 pts)
        val lostKeywords = extractKeywords("${lostItem.title} ${lostItem.publicDescription}")
        val foundKeywords = extractKeywords("${foundItem.title} ${foundItem.publicDescription}")
        val sharedKeywords = lostKeywords.intersect(foundKeywords)

        if (sharedKeywords.isNotEmpty()) {
            val wordsStr = sharedKeywords.take(3).joinToString(", ")
            totalScore += 10
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Palabras Clave",
                    pointsAwarded = 10,
                    maxPoints = 10,
                    explanation = "Términos coincidentes: $wordsStr"
                )
            )
        } else {
            breakdowns.add(
                MatchBreakdown(
                    ruleName = "Palabras Clave",
                    pointsAwarded = 0,
                    maxPoints = 10,
                    explanation = "Sin términos coincidentes en título o descripción"
                )
            )
        }

        val clampedScore = totalScore.coerceIn(0, 100)
        return MatchResult(
            lostItem = lostItem,
            foundItem = foundItem,
            score = clampedScore,
            breakdowns = breakdowns,
            isHighMatch = clampedScore >= 80
        )
    }

    private fun normalizeString(text: String): String {
        val trimmed = text.trim().lowercase()
        val normalized = Normalizer.normalize(trimmed, Normalizer.Form.NFD)
        return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }

    private fun extractKeywords(text: String): Set<String> {
        val stopWords = setOf(
            "un", "una", "unos", "unas", "el", "la", "los", "las", "de", "del", "en", "para", "por",
            "con", "sin", "mi", "se", "que", "y", "o", "a", "al", "es", "fue", "muy", "objeto", "perdi"
        )
        val normalized = normalizeString(text)
        return normalized
            .split("[^a-zA-Z0-9]+".toRegex())
            .filter { it.length >= 3 && it !in stopWords }
            .toSet()
    }

    private fun isColorSynonym(c1: String, c2: String): Boolean {
        val synonyms = listOf(
            setOf("gris", "plateado", "silver", "gray", "grey"),
            setOf("negro", "black", "oscuro"),
            setOf("azul", "blue", "celeste", "marino"),
            setOf("blanco", "white"),
            setOf("marron", "cafe", "brown"),
            setOf("rojo", "vino", "red")
        )
        return synonyms.any { group -> c1 in group && c2 in group }
    }
}
