package org.example.project.data.mapper

import org.example.project.data.entities.UserEntity
import org.example.project.domain.models.UserModel

class UserMapper {
    fun toModel(entity: UserEntity) =
        UserModel(
            name = entity.name,
            phone = entity.phone,
            email = entity.email,
            company = entity.company,
            role = entity.role,
        )

    fun toEntity(model: UserModel) =
        UserEntity(
            phone = model.phone,
            name = model.name,
            email = model.email,
            company = model.company,
            role = model.role,
        )
}