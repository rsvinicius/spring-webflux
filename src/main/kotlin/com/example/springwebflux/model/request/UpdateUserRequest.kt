package com.example.springwebflux.model.request

data class UpdateUserRequest(
    val name: String? = null,
    val age: Int? = null,
    val salary: Double? = null,
    val department: String? = null
)