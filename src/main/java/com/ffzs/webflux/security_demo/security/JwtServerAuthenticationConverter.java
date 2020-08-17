package com.ffzs.webflux.security_demo.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: ffzs
 * @Date: 2020/8/17 上午9:39
 */

@Component
@AllArgsConstructor
@Slf4j
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final String TOKEN_PREFIX = "Bearer ";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        log.info("访问 ServerAuthenticationConverter  。。。。。。。。。。。");
        if (exchange.getRequest().getPath().value().contains("auth/login")) return Mono.empty();

        return Mono.justOrEmpty(exchange)
                .flatMap(it -> {
                    var auth = it.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
                    if (auth == null) {
                        return Mono.error(new BadCredentialsException("没有authorization信息"));
                    }
                    return Mono.just(auth);
                })
                .filter(it -> {
                    if (!it.isEmpty()) return true;
                    else {
                        throw new BadCredentialsException("没有token");
                    }
                })
                .log()
                .map(it -> it.get(0))
                .filter(it -> {
                    if (it.startsWith(TOKEN_PREFIX)) return true;
                    else {
                        throw new BadCredentialsException("token没有以" + TOKEN_PREFIX + "开头");
                    }
                })
                .map(it -> it.replace(TOKEN_PREFIX, ""))
                .map(it -> {
                    Authentication auth = new UsernamePasswordAuthenticationToken(it, it);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    return auth;
                });
    }
}
