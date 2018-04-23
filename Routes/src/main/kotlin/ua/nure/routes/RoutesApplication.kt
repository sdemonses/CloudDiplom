package ua.nure.routes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class RoutesApplication

fun main(args: Array<String>) {
    runApplication<RoutesApplication>(*args)
}
