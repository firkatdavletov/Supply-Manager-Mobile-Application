package org.example.project.data.mapper

import org.example.project.data.entities.TokenPairEntity
import org.example.project.domain.models.TokenPairModel

class TokenPairMapper {
    fun toModel(entity: TokenPairEntity) = TokenPairModel(entity.access, entity.refresh)
}