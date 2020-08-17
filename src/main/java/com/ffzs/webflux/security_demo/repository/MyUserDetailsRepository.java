package com.ffzs.webflux.security_demo.repository;

import com.ffzs.webflux.security_demo.model.MyUserDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author: ffzs
 * @Date: 2020/8/12 上午9:14
 */
public interface MyUserDetailsRepository extends ReactiveCrudRepository<MyUserDetails, Long> {

    Mono<MyUserDetails> findByUsername (String username);

}
