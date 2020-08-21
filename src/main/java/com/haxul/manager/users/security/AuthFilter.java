package com.haxul.manager.users.security;

import com.haxul.manager.users.errors.AccessForbiddenException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthFilter extends HandlerInterceptorAdapter {


    @Value("${token.salt}")
    private String tokenSalt;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            var availableUrls = List.of("/users/login", "/users/signup");
            var tokenHeader = request.getHeader("Authorization");
            var url = request.getRequestURI();
            var method = request.getMethod();
            if (method.equals("OPTIONS")) return true;
            if (method.equals("POST") && availableUrls.contains(url)) return true;
            var token = tokenHeader.replace("Bearer ", "");
            var username = verifyToken(token);
            SecurityContextHolder.setUsername(username);
            return true;
        } catch (Exception e) {
            throw new AccessForbiddenException();
        }
    }

    public String verifyToken(String token) {
        var claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(tokenSalt))
                .parseClaimsJws(token).getBody();
        var expiration = claims.getExpiration();
        if (System.currentTimeMillis() > expiration.getTime()) throw new AccessForbiddenException();
        return claims.getSubject();
    }
}
