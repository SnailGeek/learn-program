package com.snail.learn.serializer;

import com.snail.learn.entity.User;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserDeserializer implements Deserializer<User> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public User deserialize(String topic, byte[] data) {
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();
        int userId = buffer.getInt();
        int length = buffer.getInt();
        System.out.println(length);

        String username = new String(data, 0, length, StandardCharsets.UTF_8);
        return new User(userId, username);
    }

    @Override
    public void close() {

    }
}
