package com.example.springwebflux.integration.repository

import com.example.springwebflux.mock.UserMock.validUserOne
import com.example.springwebflux.mock.UserMock.validUserThree
import com.example.springwebflux.mock.UserMock.validUserTwo
import com.example.springwebflux.repository.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension::class)
class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @DisplayName("Find all users containing name John ordered by age ascended successfully")
    fun findAllByNameContainsOrderByAgeAscTest() {
        val usersFound = userRepository
            .deleteAll()
            .thenMany(Flux.just(validUserOne(), validUserTwo(), validUserThree()))
            .flatMap { user -> userRepository.save(user) }
            .thenMany(userRepository.findAllByNameContainsOrderByAgeAsc("John"))

        StepVerifier
            .create(usersFound)
            .consumeNextWith { user ->
                assert(user.id == "3")
                assert(user.name == "John Oliver")
                assert(user.age == 20)
            }
            .consumeNextWith { user ->
                assert(user.id == "1")
                assert(user.name == "John William")
                assert(user.age == 25)
            }
            .verifyComplete()

        StepVerifier
            .create(usersFound)
            .expectNextCount(2)
            .verifyComplete()
    }

    @AfterEach
    fun cleanDB() {
        userRepository.findAll().flatMap { user ->
            userRepository.delete(user)
        }
    }
}