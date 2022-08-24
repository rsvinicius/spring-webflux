package com.example.springwebflux.repository

import com.example.springwebflux.model.entity.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface UserRepository : ReactiveMongoRepository<User, String> {
    fun findAllByNameContainsOrderByAgeAsc(name: String): Flux<User>
}