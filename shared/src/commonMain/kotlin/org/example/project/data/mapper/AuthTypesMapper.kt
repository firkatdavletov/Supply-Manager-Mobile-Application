package org.example.project.data.mapper

import org.example.project.data.entities.AuthTypesEntity
import org.example.project.domain.models.AuthTypesModel

class AuthTypesMapper {
    fun toModel(entity: AuthTypesEntity) = AuthTypesModel(
        types = entity.types
    )
}