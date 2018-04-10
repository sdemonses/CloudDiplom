package ua.nure.routes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
class RoutesApplication

fun main(args: Array<String>) {
    runApplication<RoutesApplication>(*args)
}
