package org.example.project.domain.models

data class GeoAddressModel(
    val city: CityModel,
    val street: String,
    val house: String,
    val entrance: Int?,
    val deliveryInfo: DeliveryInfoModel?,
    val deliveryTime: Int,
    val latitude: Double,
    val longitude: Double,
    val uri: String?,
) {
    val addressString: String
        get() {
            return buildString {
                append(street)
                append(", ")
                append(house)
                if (entrance != null) {
                    append(", подъезд ")
                    append(entrance)
                }
            }
        }

    override fun toString(): String {
        return buildString {
            append(street)
            append(", ")
            append(house)
            if (entrance != null) {
                append(", подъезд ")
                append(entrance)
            }
        }
    }
}
