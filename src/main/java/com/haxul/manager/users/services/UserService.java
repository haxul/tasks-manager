package com.haxul.manager.users.services;

import com.haxul.manager.users.dto.User;
import com.haxul.manager.users.errors.LoginFailedException;
import com.haxul.manager.users.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Value("${password.salt}")
    private String salt;

    @Value("${token.salt}")
    private String tokenSalt;

    @Value("${token.expiration}")
    private String expiration;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(String username, String password) {
        String hash = bCryptPasswordEncoder.encode(password + salt);
        return userRepository.createUser(username, hash);
    }

    public String loginUser(String username, String password) {
        var user = userRepository.findUserByUsername(username);
        if (user == null) throw new LoginFailedException();
        var isCorrectPassword = bCryptPasswordEncoder.matches(password + salt, user.getPassword());
        if (!isCorrectPassword) throw new LoginFailedException();
        return createJwtToken(username);
    }

    private String createJwtToken(String username) {
        var signatureAlgorithm = SignatureAlgorithm.HS256;
        var apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenSalt);
        var singingLey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(signatureAlgorithm, singingLey)
                .compact();
    }
}
