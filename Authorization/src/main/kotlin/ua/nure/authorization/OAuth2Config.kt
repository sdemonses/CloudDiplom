package ua.nure.authorization

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

@EnableAuthorizationServer
@Configuration
class OAuth2Config(val authenticationManager: AuthenticationManager) : AuthorizationServerConfigurerAdapter() {

    /*override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore())
    }*/

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    /*  @Bean
      fun tokenStore(): JdbcTokenStore {
          return JdbcTokenStore(dataSource)
      }*/
}