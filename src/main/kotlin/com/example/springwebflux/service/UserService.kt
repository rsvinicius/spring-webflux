package com.example.springwebflux.service

import com.example.springwebflux.model.entity.User
import com.example.springwebflux.model.request.UpdateUserRequest
import com.example.springwebflux.repository.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger {}

    fun createUser(user: User): Mono<User> {
        logger.info {
            "createUser: creating user id=${user.id}, name=${user.name}"
        }
        return userRepository.save(user).also {
            logger.info {
                "createUser: user created id=${user.id}, name=${user.name}"
            }
        }
    }

    fun getAllUsers(): Flux<User> {
        logger.info {
            "getAllUsers: finding all users"
        }
        return userRepository.findAll().also {
            logger.info {
                "getAllUsers: found users"
            }
        }
    }

    fun findUserById(id: String): Mono<User> {
        logger.info {
            "getUserById: finding user with id=${id}"
        }
        return userRepository.findById(id).also {
            logger.info {
                "getUserById: user found id=${id}"
            }
        }
    }

    fun deleteUser(id: String): Mono<User> {
        logger.info {
            "deleteUser: deleting user with id=${id}"
        }
        return userRepository.findById(id)
            .flatMap { user -> userRepository.delete(user).then(Mono.just(user)) }.also {
                logger.info {
                    "getUserById: user deleted id=${id}"
                }
            }
    }

    fun updateUser(id: String, userRequest: UpdateUserRequest): Mono<User> {
        logger.info {
            "updateUser: updating user with id=${id}"
        }
        return userRepository.findById(id)
            .flatMap { dbUser ->
                dbUser.apply {
                    name = userRequest.name ?: name
                    age = userRequest.age ?: age
                    salary = userRequest.salary ?: salary
                    department = userRequest.department ?: department
                }
                return@flatMap userRepository.save(dbUser)
            }.also {
                logger.info {
                    "updateUser: user updated id=${id}"
                }
            }
    }

    fun fetchUsers(name: String): Flux<User> {
        logger.info {
            "searchUsers: searching for users containing name=${name}"
        }
        return userRepository.findAllByNameContainsOrderByAgeAsc(name).also {
            logger.info {
                "searchUsers: found users containing name=${name}"
            }
        }
    }
}