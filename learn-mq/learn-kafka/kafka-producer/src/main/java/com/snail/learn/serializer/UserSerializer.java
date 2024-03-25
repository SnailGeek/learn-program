package com.snail.learn.serializer;


import com.snail.learn.entity.User;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class UserSerializer implements Serializer<User> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, User data) {
        try {
            if (data == null) {
                return null;
            }
            Integer userId = data.getUserId();
            String username = data.getUsername();
            int length = 0;
            byte[] bytes = null;
            if (username != null) {
                bytes = username.getBytes();
                length = bytes.length;
            }

            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + length);
            buffer.putInt(userId);
            buffer.putInt(length);
            buffer.put(bytes);
            return buffer.array();
        } catch (Exception e) {
            throw new SerializationException("序列化数据异常");
        }
    }

    @Override
    public void close() {

    }
}
