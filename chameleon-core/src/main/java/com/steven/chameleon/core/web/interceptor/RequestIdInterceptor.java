package com.steven.chameleon.core.web.interceptor;

import com.steven.chameleon.core.util.RequestIdGenerator;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RequestId拦截器
 * 负责在请求处理前后管理RequestId
 */
public class RequestIdInterceptor implements HandlerInterceptor {
    
//    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 尝试从请求头获取
        String requestId = request.getHeader(RequestIdGenerator.REQUEST_ID_HEADER);
        
        // 2. 验证获取到的requestId是否有效
        if (requestId == null || !RequestIdGenerator.isValidRequestId(requestId)) {
            requestId = RequestIdGenerator.generate();
        }
        
        // 3. 设置到MDC
        RequestIdGenerator.setRequestId(requestId);
        
        // 4. 设置到响应头
        response.setHeader(RequestIdGenerator.REQUEST_ID_HEADER, requestId);
        
        return true;
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
//                              Object handler, Exception ex) {
//        RequestIdGenerator.clear();
//    }
}