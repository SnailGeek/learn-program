package com.snail.BufferAndFile;

import java.nio.ByteBuffer;

import static com.snail.BufferAndFile.ByteBufferUtil.debugAll;

/**
 * 处理半包和黏包的问题
 */
public class TestBufferExam {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 分隔符找到完整的一行数据
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        // 此处不能用clear(),因为clear()就将buffer中的数据清掉了
        // compact的作用就是将上次没有读完的数据放在buffer的前面
        source.compact();
    }

}
