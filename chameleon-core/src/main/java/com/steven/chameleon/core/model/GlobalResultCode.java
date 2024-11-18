package com.steven.chameleon.core.model;

public class GlobalResultCode extends BaseResultCode {
    // 全局通用编码 1-100
    public static final IResultCode SUCCESS = new GlobalResultCode(0, "成功");
    public static final IResultCode FAILURE = new GlobalResultCode(1, "失败");
    public static final IResultCode PARAM_ERROR = new GlobalResultCode(2, "参数错误");
    public static final IResultCode UNAUTHORIZED = new GlobalResultCode(3, "未授权");
    
    // HTTP 状态码 100-599
    public static final IResultCode HTTP_404 = new GlobalResultCode(404, "资源不存在");
    public static final IResultCode HTTP_500 = new GlobalResultCode(500, "服务器错误");

    private GlobalResultCode(int code, String message) {
        super(code, message);
    }
}
