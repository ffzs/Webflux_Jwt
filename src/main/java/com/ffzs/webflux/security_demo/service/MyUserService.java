package com.ffzs.webflux.security_demo.service;

import com.ffzs.webflux.security_demo.model.MyUser;
import com.ffzs.webflux.security_demo.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: ffzs
 * @Date: 2020/8/11 下午5:35
 */

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final MyUserRepository myUserRepository;

    public Flux<MyUser> findAll () {
        return myUserRepository.findAll();
    }

    public Mono<MyUser> findByUsername (String username) {
        return myUserRepository.findByUsername(username);
    }

    public Mono<MyUser> save (MyUser user) {
        user.setPassword(password.encode(user.getPassword()));
        return myUserRepository.save(user);
    }

    public Mono<Void> deleteById (Long id) {
        return myUserRepository.deleteById(id);
    }
}
