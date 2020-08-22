package com.haxul.manager.chatMessages.repositories;

import com.haxul.manager.chatMessages.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.haxul.manager.chatMessages.Constants.DELIMITER;
import static com.haxul.manager.chatMessages.Constants.SENDER_PREFIX;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {

    private final Jedis jedis;

    public void save(String from, String to, String message, String time) {
        String data = message + DELIMITER + time;
        jedis.lpush(from + SENDER_PREFIX + to, data);
    }


    public List<Message> findChatWithFriend(String initiator, String friend) {
        List<String> initiatorMessages = jedis.lrange(initiator + SENDER_PREFIX + friend, 0, -1);
        List<String> friendMessages = jedis.lrange(friend + SENDER_PREFIX + initiator, 0, -1);
        Stream<Message> listInit = initiatorMessages.stream().map(data -> {
            String[] split = data.split(DELIMITER);
            return new Message(initiator, split[0], Long.parseLong(split[1]));
        });
        Stream<Message> listFriend = friendMessages.stream().map(data -> {
            String[] split = data.split(DELIMITER);
            return new Message(friend, split[0], Long.parseLong(split[1]));
        });
        return Stream.concat(listInit, listFriend).sorted((x, y) -> x.getTime() > y.getTime() ? 1 : -1).collect(Collectors.toList());
    }
}
