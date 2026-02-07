package org.example.project.domain.utils

object DateUtility {
    fun formatLocalDateTimeSimple(dateTimeString: String): String {
        // "2026-01-30T14:35:00" -> "30.01.2026"
        val parts = dateTimeString.split("T")[0].split("-")
        return "${parts[2]}.${parts[1]}.${parts[0]}"
    }
}