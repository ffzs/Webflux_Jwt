package com.ffzs.webflux.security_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author: ffzs
 * @Date: 2020/8/11 下午4:38
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@SecurityScheme(
        name = "登录",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class HelloController {

    @GetMapping
    public Mono<String> hi () {
        return Mono.just("hello ffzs!! ");
    }

    @GetMapping("role_admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "test role admin",
            security = @SecurityRequirement(name = "登录"),
            tags = {"role_admin"}
    )
    public Mono<String> testAdmin () {
        return Mono.just("Hello!! ADMIN role  !!");
    }

    @GetMapping("role_user")
    @PreAuthorize("hasAnyRole('USER')")
    public Mono<String> testUser() {
        return Mono.just("Hello!! USER role  !!");
    }


    @GetMapping(value = "hello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxString() {
        return Flux.just("ffzs", "dz", "tony", "vincent", "sleepycat")
                .repeat(10)
                .delayElements(Duration.ofMillis(1000))
                .map(i -> "hello !! " + i);
    }
}
