package org.example.project.features.utils

import org.example.project.domain.models.AddressModel

object AddressUtility {

    fun addressString(model: AddressModel): String {
        return buildString {
            append(model.street)
            append(", ")
            append(model.house)
        }
    }
}