package ua.nure.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.security.oauth2.client.token.AccessTokenProvider
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider
import java.util.stream.Collectors


@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
class GatewayApplication {
    @Bean
    fun userInfoRestTemplateCustomizer(loadBalancerInterceptor: LoadBalancerInterceptor): UserInfoRestTemplateCustomizer {
        return UserInfoRestTemplateCustomizer {
            val interceptors = mutableListOf<ClientHttpRequestInterceptor>()
            interceptors.add(loadBalancerInterceptor)
            val accessTokenProviderChain = AccessTokenProviderChain(listOf<OAuth2AccessTokenSupport>(AuthorizationCodeAccessTokenProvider(), ImplicitAccessTokenProvider(),
                    ResourceOwnerPasswordAccessTokenProvider(), ClientCredentialsAccessTokenProvider()).stream()
                    .peek { it.setInterceptors(interceptors) }
                    .collect(Collectors.toList()) as MutableList<out AccessTokenProvider>?)
            it.setAccessTokenProvider(accessTokenProviderChain)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
