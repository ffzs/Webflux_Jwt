package com.ffzs.webflux.security_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ffzs
 * @Date: 2020/8/16 下午9:45
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    String username;
    String auth;
    String token;
}
