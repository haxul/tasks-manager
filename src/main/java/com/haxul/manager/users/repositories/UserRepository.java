package com.haxul.manager.users.repositories;

import com.haxul.manager.users.dto.User;
import com.haxul.manager.users.errors.UsernameExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.sql.Timestamp;


@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final Jedis jedisClient;

    public User createUser(String username, String password) {
        if (jedisClient.exists(username)) throw new UsernameExistException();
        User user = User.builder().username(username).password(password).build();
        jedisClient.hset(username, "username", user.getUsername());
        jedisClient.hset(username, "password", user.getPassword());
        jedisClient.hset(username, "isDeleted", "false");
        jedisClient.hset(username, "isBanned", "false");
        jedisClient.hset(username, "created", user.getCreated().toString());
        return user;
    }

    public User findUserByUsername(String username) {
        if (!jedisClient.exists(username)) return null;
        String password = jedisClient.hget(username, "password");
        boolean isDeleted = Boolean.parseBoolean(jedisClient.hget(username, "isDeleted"));
        boolean isBanned = Boolean.parseBoolean(jedisClient.hget(username, "isBanned"));
        Timestamp created = Timestamp.valueOf(jedisClient.hget(username, "created"));
        return new User(username, password, isDeleted, isBanned, created);

    }
}
