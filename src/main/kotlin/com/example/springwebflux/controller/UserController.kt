package com.example.springwebflux.controller

import com.example.springwebflux.model.entity.User
import com.example.springwebflux.model.request.UpdateUserRequest
import com.example.springwebflux.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "UserController", description = "User")
class UserController(private val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create user")
    fun createUser(@RequestBody user: User): Mono<User> {
        return userService.createUser(user)
    }

    @GetMapping
    @Operation(summary = "Get all users")
    fun getAllUsers(): Flux<User> {
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    fun getUserById(@PathVariable id: String): Mono<User> {
        return userService.findUserById(id)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    fun deleteUser(@PathVariable id: String): Mono<Void> {
        return userService.deleteUser(id)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    fun updateUser(@PathVariable id: String, @RequestBody userRequest: UpdateUserRequest): Mono<User> {
        return userService.updateUser(id, userRequest)
    }

    @GetMapping("/search")
    @Operation(summary = "Search users")
    fun searchUsers(@RequestParam name: String): Flux<User> {
        return userService.fetchUsers(name)
    }
}