package com.haxul.manager.users.repositories;

import com.haxul.manager.users.entities.User;
import com.haxul.manager.users.errors.UsernameExistException;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class UserRepository {

    private final Jedis jedisClient;
    private final String USERS_LIST = "users";

    public UserRepository(Jedis jedisClient) {
        this.jedisClient = jedisClient;
    }

    public User createUser(String username, String password) {
        if (jedisClient.exists(username)) throw new UsernameExistException();
        User user = User.builder().username(username).password(password).build();
        jedisClient.hset(username, "username", user.getUsername());
        jedisClient.hset(username, "password", user.getPassword());
        jedisClient.hset(username, "isDeleted", "false");
        jedisClient.hset(username, "isBanned", "false");
        jedisClient.hset(username, "created", user.getCreated().toString());
        jedisClient.sadd(USERS_LIST, username);
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

    public List<User> findAllUsers() {
        Set<String> usernames = jedisClient.smembers(USERS_LIST);
        if (usernames.isEmpty()) return new ArrayList<>();
        return usernames.stream().map(this::findUserByUsername).collect(Collectors.toList());
    }
}
