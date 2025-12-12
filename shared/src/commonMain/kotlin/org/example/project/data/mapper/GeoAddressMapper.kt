package org.example.project.data.mapper

import org.example.project.data.entities.GeoAddressEntity
import org.example.project.domain.models.GeoAddressModel

class GeoAddressMapper(
    private val cityMapper: CityMapper,
    private val deliveryInfoMapper: DeliveryInfoMapper,
) {
    fun toModel(entity: GeoAddressEntity) = GeoAddressModel(
        city = cityMapper.toModel(entity.city),
        street = entity.street,
        house = entity.house,
        entrance = entity.entrance,
        deliveryInfo = entity.deliveryInfo?.let { deliveryInfoMapper.toModel(it) },
        deliveryTime = entity.deliveryTime,
        latitude = entity.latitude,
        longitude = entity.longitude,
        uri = entity.uri,
    )

    fun toModel(entities: List<GeoAddressEntity>) = entities.map { toModel(it) }
}