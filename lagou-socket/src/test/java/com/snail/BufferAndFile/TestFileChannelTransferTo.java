package com.snail.BufferAndFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestFileChannelTransferTo {
    public static void main(String[] args) {

        try (FileChannel to = new FileOutputStream("to.txt").getChannel();
             FileChannel from = new FileInputStream("words2.txt").getChannel()) {
            // 效率高，底层会利用操作系统的零拷贝进行优化,最多只能拷贝2g的数据
//            from.transferTo(0, from.size(), to);
            // 下面是传输多余2g的数据时的方法
            long size = from.size();
            // left表示还剩余多少字节没有拷贝
            for (long left = size; left > 0; ) {
                left -= to.transferTo((size - left), size, to);
            }

        } catch (IOException e) {
        }
    }
}
