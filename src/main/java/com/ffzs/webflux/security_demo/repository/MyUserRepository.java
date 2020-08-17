package com.ffzs.webflux.security_demo.repository;

import com.ffzs.webflux.security_demo.model.MyUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author: ffzs
 * @Date: 2020/8/11 下午5:37
 */


public interface MyUserRepository extends ReactiveCrudRepository<MyUser, Long> {

    Mono<MyUser> findByUsername(String username);

}
