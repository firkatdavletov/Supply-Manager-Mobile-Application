package org.example.project.data.api.departments_api

import org.example.project.data.api.departments_api.model.GetDepartmentsResponse

interface DepartmentApi {
    suspend fun getDepartments(): GetDepartmentsResponse
}