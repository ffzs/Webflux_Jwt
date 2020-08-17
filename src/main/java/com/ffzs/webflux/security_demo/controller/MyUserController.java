package com.ffzs.webflux.security_demo.controller;

import com.ffzs.webflux.security_demo.model.MyUser;
import com.ffzs.webflux.security_demo.service.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: ffzs
 * @Date: 2020/8/11 下午5:32
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class MyUserController {

    private final MyUserService myUserService;

    @GetMapping("findAll")
    public Flux<MyUser> findAll(){
        return myUserService.findAll();
    }

    @GetMapping
    public Mono<MyUser> findByUsername(@RequestParam("username") String username){
        return myUserService.findByUsername(username);
    }

    @PostMapping
    public Mono<MyUser> save(@RequestBody MyUser user){
        return myUserService.save(user);
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestParam("id") Long id){
        return myUserService.deleteById(id);
    }
}
