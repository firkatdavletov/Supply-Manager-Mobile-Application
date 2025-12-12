package org.example.project.features.home

data class OrderUIModel(
    val id: Long,
    val number: String,
    val status: String,
    val amount: Int,
)