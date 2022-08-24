package com.example.springwebflux.mock

import com.example.springwebflux.model.entity.User

object UserMock {
    fun validUserOne() = User(id = "1", name = "John William", age = 25, salary = 16000.0, department = "IT")
    fun validUserTwo() = User(id = "2", name = "Yulia James", age = 23, salary = 10000.0, department = "HR")
    fun validUserThree() = User(id = "3", name = "John Oliver", age = 20, salary = 10000.0, department = "IT")
    fun updatedUser() = User(id = "3", name = "John Oliver", age = 20, salary = 14000.0, department = "IT")
}