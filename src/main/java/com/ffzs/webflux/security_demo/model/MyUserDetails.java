package com.ffzs.webflux.security_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: ffzs
 * @Date: 2020/8/12 上午9:09
 */

@Table("my_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDetails implements UserDetails {

    @Id
    private Long id;
    private String username;
    private String password;
    private String authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

/*  第一个：账户没有过期
    第二个：账户没被锁定 （是否冻结）
    第三个：密码没有过期
    第四个：账户是否可用（是否被删除）*/

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
