package com.ffzs.webflux.security_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ffzs
 * @Date: 2020/8/16 下午10:23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResult {

    private int code = 200;
    private String msg;
    private Object data;
}
