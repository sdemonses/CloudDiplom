package ua.nure.authorization

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter

@Configuration
class SecurityConfig: GlobalAuthenticationConfigurerAdapter() {

    override fun init(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN")
    }
}