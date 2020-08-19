package com.ffzs.webflux.security_demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffzs.webflux.security_demo.model.HttpResult;
import com.ffzs.webflux.security_demo.model.LoginResponse;
import com.ffzs.webflux.security_demo.model.MyUser;
import com.ffzs.webflux.security_demo.repository.MyUserDetailsRepository;
import com.ffzs.webflux.security_demo.service.JwtSigner;
import com.ffzs.webflux.security_demo.service.MyUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author: ffzs
 * @Date: 2020/8/16 下午8:52
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Slf4j
public class LoginController {

    private final MyUserDetailsRepository myUserRepository;
    private final MyUserService myUserService;
    private final JwtSigner jwtSigner;
    private final PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostMapping("login")
    public Mono<HttpResult> login (@RequestBody Map<String, String> user) {

        ObjectMapper mapper = new ObjectMapper();

        return Mono.just(user.get("username"))
                .flatMap(myUserRepository::findByUsername)
                .doOnNext(i -> log.info("{}", i))
                .filter(it -> password.matches(user.get("password"), it.getPassword()))
                .map(it -> {
                    try {
                        return new HttpResult(HttpStatus.OK.value(),
                                "成功登录",
                                new LoginResponse(it.getUsername(),
                                        mapper.writeValueAsString(it
                                                .getAuthorities()
                                                .stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList())),
                                        jwtSigner.generateToken(it)));
                    } catch (JsonProcessingException e) {
                        return new HttpResult();
                    }
                })
                .onErrorResume(e -> Mono.empty())
                .switchIfEmpty(Mono.just(new HttpResult(HttpStatus.UNAUTHORIZED.value(), "登录失败", null)));
    }

//    @CrossOrigin
    @PostMapping("signup")
    public Mono<HttpResult> signUp (@RequestBody MyUser user) {

        return Mono.just(user)
                .map(myUserService::save)
                .map(it -> new HttpResult(HttpStatus.OK.value(), "注册成功", null))
                .onErrorResume(e -> Mono.just(new HttpResult(HttpStatus.UNAUTHORIZED.value(), "注册失败", e)));
    }
}
