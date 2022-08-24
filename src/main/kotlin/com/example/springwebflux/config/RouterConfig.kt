package com.example.springwebflux.config

import com.example.springwebflux.handler.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig(private val handler: UserHandler) {
    private val usersRequestMapping = "/api/v1/handler/users"

    @Bean
    fun route() = router {
        usersRequestMapping.nest {
            accept(MediaType.APPLICATION_JSON).nest {
                POST("", handler::createUser)
                GET("", handler::getAllUsers)
                GET("/search", handler::searchUsers)
                GET("/{id}", handler::getUserById)
                POST("/{id}", handler::deleteUser)
                PUT("/{id}", handler::updateUser)
            }
        }
    }
}