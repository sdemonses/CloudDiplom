package ua.nure.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
import org.springframework.security.web.session.SessionManagementFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.WebUtils
import java.util.*
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableOAuth2Sso
class GatewayApplication {

    @Configuration
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    protected class SecurityConfiguration : WebSecurityConfigurerAdapter() {


        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http
                    .authorizeRequests()
                    //Allow access to all static resources without authentication
                    .antMatchers("/", "/**/*.html").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .csrf().csrfTokenRepository(csrfTokenRepository())
                    .and()
                    .addFilterAfter(csrfHeaderFilter(), SessionManagementFilter::class.java)

            //http.httpBasic().disable();
        }

        private fun csrfHeaderFilter(): Filter {
            return object : OncePerRequestFilter() {
                override fun doFilterInternal(request: HttpServletRequest,
                                              response: HttpServletResponse, filterChain: FilterChain) {
                    val csrf = request.getAttribute(CsrfToken::class.java.name) as CsrfToken
                    if (csrf != null) {
                        var cookie: Cookie? = WebUtils.getCookie(request, "XSRF-TOKEN")
                        val token = csrf.token
                        if (cookie == null || token != null && token != cookie.value) {
                            cookie = Cookie("XSRF-TOKEN", token)
                            cookie.path = "/"
                            response.addCookie(cookie)
                        }
                    }
                    filterChain.doFilter(request, response)
                }
            }
        }

        private fun csrfTokenRepository(): CsrfTokenRepository {
            val repository = HttpSessionCsrfTokenRepository()
            repository.setHeaderName("X-XSRF-TOKEN")
            return repository
        }
    }
}

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
internal class WorkaroundRestTemplateCustomizer : UserInfoRestTemplateCustomizer {

    override fun customize(template: OAuth2RestTemplate) {
        template.interceptors = ArrayList(template.interceptors)
    }

}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
