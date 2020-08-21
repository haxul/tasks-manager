package com.haxul.manager.users.repositories;

import com.haxul.manager.users.entities.User;
import com.haxul.manager.users.errors.UsernameExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final Jedis jedisClient;

    public User createUser(String username, String password) {
        if (jedisClient.exists(username)) throw new UsernameExistException();
        Pipeline pipeline = jedisClient.pipelined();
        pipeline.multi();
        User user = User.builder().username(username).password(password).build();
        pipeline.hset(username, "username", user.getUsername());
        pipeline.hset(username, "password", user.getPassword());
        pipeline.hset(username, "isDeleted", "false");
        pipeline.hset(username, "isBanned", "false");
        pipeline.hset(username, "created", user.getCreated().toString());
        pipeline.sadd("users", username);
        pipeline.exec();
        pipeline.close();
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
        Set<String> usernames = jedisClient.smembers("users");
        if (usernames.isEmpty()) return new ArrayList<>();
        return usernames.stream().map(this::findUserByUsername).collect(Collectors.toList());
    }
}
