package org.example.project.data.api

interface ResponseModel {
    val success: Boolean
    val error: String?
    val code: Int?
}