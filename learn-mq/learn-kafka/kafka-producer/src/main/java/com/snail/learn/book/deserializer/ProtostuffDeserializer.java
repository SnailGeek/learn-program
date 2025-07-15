package com.snail.learn.book.deserializer;

import com.snail.learn.book.serializer.Company;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ProtostuffDeserializer implements Deserializer<Company> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Company deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        Schema<Company> schema = RuntimeSchema.getSchema(Company.class);
        Company company = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, company, schema);
        return company;
    }

    @Override
    public void close() {

    }
}
