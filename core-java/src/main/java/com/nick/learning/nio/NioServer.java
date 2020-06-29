package com.nick.learning.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NioServer implements Runnable{

    private Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public NioServer(int port){
        try{
            this.selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server started on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try{
            while (true){
                int wait = selector.select();
                if(wait == 0){
                    continue;
                }
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()){
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    if(selectionKey.isValid()){
                        if(selectionKey.isAcceptable()){

                        }else if(selectionKey.isReadable()){

                        }else if(selectionKey.isWritable()){

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
