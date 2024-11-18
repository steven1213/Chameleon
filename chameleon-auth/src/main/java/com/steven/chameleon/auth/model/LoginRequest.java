package com.steven.chameleon.auth.model;

import com.steven.chameleon.auth.enums.LoginType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @NotNull(message = "登录类型不能为空")
    private LoginType loginType;
    
    private String captcha;

    private String sessionId;
}
