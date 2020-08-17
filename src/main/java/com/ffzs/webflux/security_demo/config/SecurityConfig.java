package com.ffzs.webflux.security_demo.config;


import com.ffzs.webflux.security_demo.handler.JwtWebFilter;
import com.ffzs.webflux.security_demo.security.SecurityContextRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author: ffzs
 * @Date: 2020/8/11 下午4:22
 */

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
                ServerHttpSecurity http,
                JwtWebFilter jwtWebFilter
        ) {

        return http
                .authorizeExchange()
                .pathMatchers("/auth/login", "/auth/signup").permitAll()
                .pathMatchers("/v3/api-docs/**", "/swagger-resources/configuration/ui",
                        "/swagger-resources","/swagger-resources/configuration/security",
                        "/swagger-ui.html","/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index").permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAfter(jwtWebFilter, SecurityWebFiltersOrder.FIRST)  // 这里注意执行位置一定要在securityContextRepository
                .securityContextRepository(securityRepository)
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .logout().disable()
                .build();
    }
}
