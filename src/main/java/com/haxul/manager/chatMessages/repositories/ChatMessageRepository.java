package com.haxul.manager.chatMessages.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import static com.haxul.manager.chatMessages.Constants.*;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {

    private final Jedis jedis;

    public void save(String from, String to, String message, String time) {
        String data = message + DELIMITER + time;

        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();
        pipeline.sadd(from + SENDER_PREFIX + to, data);
        pipeline.sadd(to + RECEIVER_PREFIX + from, data);
        pipeline.exec();
        pipeline.close();
    }
}
