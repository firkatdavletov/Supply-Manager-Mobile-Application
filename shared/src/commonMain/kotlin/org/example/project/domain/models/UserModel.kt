package org.example.project.domain.models

import org.example.project.data.entities.UserEntity

data class UserModel(
    val name: String,
    val phone: String,
    val email: String,
    val company: String?
)
