package ua.nure.authorization

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.SessionAttributes
import java.security.Principal


@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
@RestController
@SessionAttributes("authorizationRequest")
class AuthorizationApplication {

    @GetMapping("/user")
    @ResponseBody
    fun user(user: Principal): Principal {
        return user
    }
}

fun main(args: Array<String>) {
    runApplication<AuthorizationApplication>(*args)
}
