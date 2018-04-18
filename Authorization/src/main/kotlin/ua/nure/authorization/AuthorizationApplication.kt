package ua.nure.authorization

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@SpringBootApplication
@EnableAuthorizationServer
@EnableEurekaClient
@RestController
class AuthorizationApplication {

    @GetMapping("/user")
    fun getUser(user: Principal): Principal {
        return user
    }
}

fun main(args: Array<String>) {
    runApplication<AuthorizationApplication>(*args)
}
