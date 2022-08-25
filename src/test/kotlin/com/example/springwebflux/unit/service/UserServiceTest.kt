package com.example.springwebflux.unit.service

import com.example.springwebflux.mock.UserMock.updatedUser
import com.example.springwebflux.mock.UserMock.validUserOne
import com.example.springwebflux.mock.UserMock.validUserThree
import com.example.springwebflux.mock.UserMock.validUserTwo
import com.example.springwebflux.model.request.UpdateUserRequest
import com.example.springwebflux.repository.UserRepository
import com.example.springwebflux.service.UserService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


@ExtendWith(MockKExtension::class)
class UserServiceTest {

    @InjectMockKs
    private lateinit var userService: UserService

    @MockK
    private lateinit var userRepository: UserRepository


    @Test
    @DisplayName("Create user successfully")
    fun testCreateUser() {
        every {
            userRepository.save(any())
        } returns Mono.just(validUserOne())

        val user = userService.createUser(validUserOne())

        StepVerifier
            .create(user)
            .consumeNextWith { u ->
                assert(u.id == "1")
                assert(u.name == "John William")
                assert(u.age == 25)
                assert(u.salary == 16000.0)
                assert(u.department == "IT")
            }
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    @DisplayName("Get all users successfully")
    fun getAllUsersTest() {
        every {
            userRepository.findAll()
        } returns Flux.just(validUserOne(), validUserTwo(), validUserThree())

        val users = userService.getAllUsers()

        StepVerifier
            .create(users)
            .consumeNextWith { u ->
                assert(u.id == "1")
                assert(u.name == "John William")
            }
            .consumeNextWith { u ->
                assert(u.id == "2")
                assert(u.name == "Yulia James")
            }
            .consumeNextWith { u ->
                assert(u.id == "3")
                assert(u.name == "John Oliver")
            }
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    @DisplayName("Find user by ID successfully")
    fun findUserByIdTest() {
        every {
            userRepository.findById("2")
        } returns Mono.just(validUserTwo())

        val user = userService.findUserById("2")

        StepVerifier
            .create(user)
            .consumeNextWith { u ->
                assert(u.id == "2")
                assert(u.name == "Yulia James")
                assert(u.age == 23)
                assert(u.salary == 10000.0)
                assert(u.department == "HR")
            }
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    @DisplayName("Delete user successfully")
    fun deleteUserTest() {
        every {
            userRepository.deleteById("1")
        } returns Mono.empty()

        val deletedUser = userService.deleteUser("1")

        StepVerifier
            .create(deletedUser)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    @DisplayName("Update user successfully")
    fun updateUserTest() {
        every {
            userRepository.findById("3")
        } returns Mono.just(validUserThree())

        every {
            userRepository.save(any())
        } returns Mono.just(updatedUser())

        val user = userService.updateUser("3", UpdateUserRequest(salary = 14000.0))

        StepVerifier
            .create(user)
            .consumeNextWith { u ->
                assert(u.id == "3")
                assert(u.name == "John Oliver")
                assert(u.age == 20)
                assert(u.salary == 14000.0)
                assert(u.department == "IT")
            }
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    @DisplayName("Fetch all users containing name John ordered by age ascended successfully")
    fun fetchUsersTest() {
        every {
            userRepository.findAllByNameContainsOrderByAgeAsc(any())
        } returns Flux.just(validUserThree(), validUserOne())

        val users = userService.fetchUsers("John")

        StepVerifier
            .create(users)
            .consumeNextWith { u ->
                assert(u.id == "3")
                assert(u.name == "John Oliver")
                assert(u.age == 20)
            }
            .consumeNextWith { u ->
                assert(u.id == "1")
                assert(u.name == "John William")
                assert(u.age == 25)
            }
            .expectNextCount(0)
            .verifyComplete()
    }
}