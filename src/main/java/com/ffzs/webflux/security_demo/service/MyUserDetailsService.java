package com.ffzs.webflux.security_demo.service;

import com.ffzs.webflux.security_demo.repository.MyUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author: ffzs
 * @Date: 2020/8/12 上午9:13
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements ReactiveUserDetailsService {

    private final MyUserDetailsRepository myUserDetailsRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return myUserDetailsRepository.findByUsername(username)
                .cast(UserDetails.class);
    }
}
