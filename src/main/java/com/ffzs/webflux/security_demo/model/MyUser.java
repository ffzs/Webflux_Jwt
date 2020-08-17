package com.ffzs.webflux.security_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


/**
 * @author: ffzs
 * @Date: 2020/8/11 下午5:10
 */

@Table("my_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyUser {

    @Id
    Long id;
    String username;
    String password;
    String authorities;

}
