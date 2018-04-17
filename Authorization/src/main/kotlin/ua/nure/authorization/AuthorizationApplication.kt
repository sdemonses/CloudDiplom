package ua.nure.authorization

import org.apache.catalina.filters.RequestDumperFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@SpringBootApplication
@EnableAuthorizationServer
@EnableEurekaClient
@RestController
@SessionAttributes("authorizationRequest")
class AuthorizationApplication {

    @Configuration
    internal class MvcConfig : WebMvcConfigurerAdapter() {
        override fun addViewControllers(registry: ViewControllerRegistry?) {
            registry!!.addViewController("login").setViewName("login")
            registry.addViewController("/oauth/confirm_access").setViewName("authorize")
            registry.addViewController("/").setViewName("index")
        }
    }

    @Configuration
    internal class LoginConfig : WebSecurityConfigurerAdapter() {
        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                        .requestMatchers()
                        .antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access")
                    .and()
                        .authorizeRequests()
                        .anyRequest().authenticated()

        }
    }

    @Profile("!cloud")
    @Bean
    internal fun requestDumperFilter(): RequestDumperFilter {
        return RequestDumperFilter()
    }
}

fun main(args: Array<String>) {
    runApplication<AuthorizationApplication>(*args)
}
