package com.zab.io.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * nio server
 *
 * @author zab
 * @date 2020-01-04 15:51
 */
public class NIOServer {

    public static void main(String[] args) throws Exception {
        //打开selector
        Selector selector = Selector.open();

        //打开一个服务端的channel
        ServerSocketChannel channel = ServerSocketChannel.open();

        //把服务端的channel绑到本机9999端口,配置为非阻塞式
        channel.socket().bind(new InetSocketAddress("127.0.0.1", 9999));
        channel.configureBlocking(false);

        //把channel注册到selector上
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //阻塞方法，直到一个注册的channel发生了事件（连接、读写）
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while(keyIterator.hasNext()) {

                SelectionKey key = keyIterator.next();

                if(key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.

                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.

                } else if (key.isReadable()) {
                    // a channel is ready for reading

                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }

                keyIterator.remove();
            }

        }

    }

    private static void handle(SelectionKey key) throws Exception {
        if (key.isAcceptable()) {
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel sc = channel.accept();
            sc.configureBlocking(false);

            sc.register(key.selector(), SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(512);
                buffer.clear();
                int len = sc.read(buffer);
                if (len != -1) {
                    System.out.println(new String(buffer.array(), 0, len));
                }
                ByteBuffer wrap = ByteBuffer.wrap("hello client".getBytes());
                sc.write(wrap);
            } finally {
                if(sc != null){
                    sc.close();
                }
            }
        }
    }
}
