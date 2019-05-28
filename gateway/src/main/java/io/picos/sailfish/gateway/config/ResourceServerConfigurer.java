package io.picos.sailfish.gateway.config;

import io.picos.sailfish.gateway.impl.support.DefaultGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Autowired
    private DefaultGatewayProperties gatewayProperties;

    @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(gatewayProperties.getOauth2ClientId());
        tokenServices.setClientSecret(gatewayProperties.getOauth2ClientSecret());
        tokenServices.setCheckTokenEndpointUrl(gatewayProperties.getOauth2CheckTokenUrl());
        return tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(gatewayProperties.getOauth2ResourceId());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .requestMatchers()
            .antMatchers("/**")
            .and()
            .authorizeRequests()
            .antMatchers("/", "/info", "/health") //actuator
            .permitAll()
            .anyRequest()
            .authenticated();
    }

}
