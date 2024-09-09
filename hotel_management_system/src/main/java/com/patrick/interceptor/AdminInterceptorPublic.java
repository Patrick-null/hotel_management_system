package com.patrick.interceptor;


import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrick.bean.RespBean;
import com.patrick.excetion.MyException;
import com.patrick.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AdminInterceptorPublic implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws MyException, IOException {

        //判断浏览器发送过来的jwt是否正确
        //正确 - 放行
        //错误 - 拦截
        //对OPTIONS请求放行，不然会出现跨域问题
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            System.out.println("------------------------");
            return true;
        }
        //获取token
        String token = request.getHeader("token");
        RespBean respBean = null;
        try {
            //解析JWT，如果出现问题会抛出异常
            Map<String,Object> map = JwtUtil.parseJwtToMap(token);
            //获取新的jwt
            String jwt = JwtUtil.generateJwt(map);
            //将jwt放到response的响应头
            response.setHeader("token",jwt);
            System.out.println("123123123123123123");
            
            response.setHeader("Access-Control-Expose-Headers","token");
            return true;
        } catch (SignatureVerificationException e) {
            respBean = RespBean.error("无效签名");
        } catch (TokenExpiredException e) {
            respBean = RespBean.error("令牌超时");
        } catch (AlgorithmMismatchException e) {
            respBean = RespBean.error("算法不匹配");
        } catch (Exception e) {
            respBean = RespBean.error("令牌无效");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(), respBean);
        return false;

    }

}
