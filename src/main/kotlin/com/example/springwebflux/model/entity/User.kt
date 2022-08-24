package com.example.springwebflux.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "users")
data class User(
    @Id
    val id: String,
    var name: String,
    var age: Int,
    var salary: Double,
    var department: String
)