package com.snail.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Component
public class MyAuthenticationService {
    public boolean check(Authentication authentication, HttpServletRequest request) {

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        if ("admin".equalsIgnoreCase(username)) {
            return true;
        } else {
            for (GrantedAuthority authority : authorities) {
                if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                    return true;
                }
            }

            if (request.getRequestURI().contains("/product")) {
                for (GrantedAuthority authority : authorities) {
                    if ("ROLE_PRODUCT".equals(authority.getAuthority())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
