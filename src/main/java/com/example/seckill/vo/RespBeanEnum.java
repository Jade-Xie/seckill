package com.example.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),
    LOGIN_ERROR(500210, "用户名或密码不正确"),
    MOBILE_ERROR(500211, "手机号码不正确"),
    BINDING_ERROR(500212, "参数校验异常"),
    EMPTY_STOCK(500501, "库存为空"),
    REPEAT_ERR(500502, "每人限购一件"),
    SESSION_ERROR(500503, "用户不存在");


    private final Integer code;
    private final String message;
}
