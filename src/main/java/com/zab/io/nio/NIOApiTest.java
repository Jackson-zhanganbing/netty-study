package com.zab.io.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOApiTest {
    public static void main(String[] args) throws Exception {
        fileCopyByNio("/Users/zhanganbing/Public/重要文件/test.docx", "/Users/zhanganbing/Public/重要文件/test2.docx");
    }

    /**
     * nio文件复制
     *
     * @param [a, b]
     * @return void
     */
    private static void fileCopyByNio(String a, String b) throws Exception {

        FileChannel channela = new RandomAccessFile(a, "rw").getChannel();
        FileChannel channelb = new RandomAccessFile(b, "rw").getChannel();

        //channela.transferTo(0,channela.size(),channelb);

        channelb.transferFrom(channela, 0, channela.size());

    }

    /**
     * nio读取文件方式
     *
     * @return void
     */
    private static void channelTest() throws Exception {
        //读取文件
        RandomAccessFile accessFile = new RandomAccessFile("/Users/zhanganbing/Public/重要文件/test.docx", "rw");
        //获取通道
        FileChannel channel = accessFile.getChannel();
        //分配内存 48bytes
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        //用通道把内存写满
        int read = channel.read(byteBuffer);
        while (read != -1) {
            System.out.println(read);
            //翻转读写模式，buffer由写模式改为读模式
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println((char) byteBuffer.get());
            }
            //清空，可写状态
            byteBuffer.clear();
            read = channel.read(byteBuffer);
        }
        accessFile.close();
    }
}
