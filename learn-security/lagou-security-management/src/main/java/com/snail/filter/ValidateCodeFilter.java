package com.snail.filter;

import com.snail.controller.ValidateCodeController;
import com.snail.exception.ValidateCodeException;
import com.snail.service.impl.MyAuthProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MyAuthProcessService myAuthProcessService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/login") && request.getMethod().equalsIgnoreCase("post")) {
            try {
                validateCode(request);
            } catch (ValidateCodeException e) {
                myAuthProcessService.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String redisKey = ValidateCodeController.REDIS_KEY_IMAGE_CODE + "-" + remoteAddr;
        String code = stringRedisTemplate.boundValueOps(redisKey).get();
        String imageCode = request.getParameter("imageCode");
        if (!StringUtils.hasText(imageCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (code == null) {
            throw new ValidateCodeException("验证码已过期");
        }

        if (!imageCode.equalsIgnoreCase(code)) {
            throw new ValidateCodeException("验证码错误");
        }

        stringRedisTemplate.delete(redisKey);
    }
}
