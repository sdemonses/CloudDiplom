package ua.nure.routes

import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.stereotype.Component

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
class SecurityConfig : WebSecurityConfigurerAdapter() {


}

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class WorkaroundRestTemplateCustomizer : UserInfoRestTemplateCustomizer {

    override fun customize(template: OAuth2RestTemplate) {
        template.interceptors = template.interceptors.toMutableList()
    }

}