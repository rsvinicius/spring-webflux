package com.example.springwebflux.handler

import com.example.springwebflux.model.entity.User
import com.example.springwebflux.model.request.UpdateUserRequest
import com.example.springwebflux.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class UserHandler(private val userService: UserService) {

    fun createUser(request: ServerRequest): Mono<ServerResponse> {
        val user = request.bodyToMono(User::class.java).single()
        return user.flatMap { u ->
            ServerResponse
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.createUser(u), User::class.java)
        }
    }

    fun getAllUsers(request: ServerRequest): Mono<ServerResponse> {
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.getAllUsers(), User::class.java)
    }

    fun getUserById(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        return userService.findUserById(id)
            .flatMap { user ->
                ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(user, User::class.java)
            }
    }

    fun deleteUser(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        return userService.deleteUser(id)
            .flatMap { user ->
                ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(user, User::class.java)
            }
    }

    fun updateUser(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        val updatedUser = request.bodyToMono(UpdateUserRequest::class.java).single()
        return updatedUser.flatMap { u ->
            ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.updateUser(id, u), User::class.java)
        }
    }

    fun searchUsers(request: ServerRequest): Mono<ServerResponse> {
        val queryName = request.queryParam("name").orElse("")
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(this.userService.fetchUsers(queryName), User::class.java)
    }
}