package org.example.project.domain.utils

import org.example.project.domain.models.AddressModel
import org.example.project.domain.models.GeoAddressModel

object AddressUtility {
    fun makeAddressString(model: AddressModel) = buildString {
        append(model.street)
        append(", ")
        append(model.house)
    }
}