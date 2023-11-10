package com.geek.design.netty.itcast.protocol;

import com.geek.design.netty.itcast.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
//ByteToMessageCodec继承的子类不能设置为shared的，具体可以看ByteToMessageCodec的源码
//@ChannelHandler.Sharable
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // 魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        // 1字节版本
        out.writeByte(1);
        // 1字节序列化方式 0:jdk, 1:json
        out.writeByte(0);
        // 1字节指令类型
        out.writeByte(msg.getMessageType());
        // 4字节序列号
        out.writeInt(msg.getSequenceId());
        // 无意义，对其填充
        out.writeByte(0xff);
        // 获取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        // 长度
        out.writeInt(bytes.length);
        // 内容
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializeType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) ois.readObject();
        log.debug("{},{},{},{},{},{}", magicNum, version, serializeType, messageType, sequenceId, length);
        log.debug("{}", message);
    }
}
