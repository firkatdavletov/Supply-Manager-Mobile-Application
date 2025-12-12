package org.example.project.data.mapper

import org.example.project.data.entities.CityEntity
import org.example.project.domain.models.CityModel

class CityMapper {
    fun toModel(entity: CityEntity): CityModel {
        return CityModel(
            id = entity.id,
            name = entity.name,
            latitude = entity.latitude,
            longitude = entity.longitude,
            subCities = toModel(entity.subCities)
        )
    }

    fun toModel(entities: List<CityEntity>): List<CityModel> {
        return entities.map { toModel(it) }
    }

    fun toEntity(model: CityModel): CityEntity {
        return CityEntity(
            id = model.id,
            name = model.name,
            latitude = model.latitude,
            longitude = model.longitude,
            subCities = toEntity(model.subCities)
        )
    }

    fun toEntity(models: List<CityModel>): List<CityEntity> {
        return models.map { toEntity(it) }
    }

}