package com.snail.selector.read;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static com.snail.BufferAndFile.ByteBufferUtil.debugAll;

@Slf4j
public class SP3_SelectServer {

    /**
     * 如果客户端传输的内容要比服务端接收的buffer长，则split方法就不能正常的处理
     * 比如一个buffer的大小是5,客户端输入的内容是abcde7777\n，则按照split方法则只会接收到7777
     * 这是因为输入的内容超过了buffer，一次不能读完，会触发两次read事件，
     * （1）第一次读事件，buffer中的内容是abcde，判断条件source.get(i) == '\n' 条件并不成立,因此第一次不会读入。
     * （2）第二次读事件，buffer中的内容是7777\n，找到完整一行，因此最终输出的是7777
     * <p>
     * 解决方法：
     * 1. Bytebuffer是一个局部变量，前一次的读事件的内容不会保留，因此需要将其设置成全局的量，能否将其放在while(true)外面呢？答案是不能，
     * 因为会被不同的channl混用，因此需要将每个channel都有一个自己的buffer，那如何做呢，就用到了attachment，SocketChannel.register()
     * 方法中第3个参数就是attachment，将Bytebuffer作为attachment传入，就将channel拥有了一个自己的buffer
     * 2. 如果第一次读完没有发现分隔符，则创建一个新的容量为原来2倍的buffer，同时将原来buffer中的内容拷贝到新buffer中，
     * 并将channel中的buffer替换为新buffer
     */

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9999));
        ssc.configureBlocking(false);

        // 创建selector
        Selector selector = Selector.open();
        // 将selector注册到ServerSocketChannel上
        // 通过sscKey可以知道将来发生的事件以及和哪个Channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        log.debug("register key: {}", sscKey);
        // 设置sscKey感兴趣的事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        while (true) {
            // 阻塞等待连接事件
            // select() 再事件未被处理时，不会被阻塞
            selector.select();
            // selectedKeys中包含了所有事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 不同的事件走不同的处理分支
                if (key.isAcceptable()) {
                    log.debug("key:{}", key);
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    log.debug("sc:{}", sc);
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);
                        if (read == -1) {
                            // 如果客户端正常close也要取消key，否则这个key不会被消耗调
                            key.cancel();
                        } else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(2 * buffer.capacity());
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        // 如果客户端异常断开连接，需要取消，否则key会一直存在
                        key.cancel();
                        throw new RuntimeException(e);
                    }
                }
                // 如果一个事件不想处理可以调用cancel方法，一个事件不能不处理，否则会陷入死循环
                // key.cancel();

                //处理完之后的事件必须要删除，如果不删除后面会有问题
                //演示场景：1. 启动服务，2. 启动客户端，客户端发送数据，服务端会报一个空指针，
                // 这是因为连接的事件已经被处理了，但是再slectedKeys中并没有被删除，导致遍历的时候channel.accpet()方法得到一个空指针
                //
                iterator.remove();
            }
        }
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
