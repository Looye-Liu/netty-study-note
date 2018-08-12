package com.stude.netty.server.server;

import com.stude.netty.server.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by looye on 2018/8/12.
 *
 * @author looye
 * @date 2018/8/12
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int portParam = 8787;
        if (args.length == 1) {
            portParam = Integer.parseInt(args[0]);
        }
        System.out.println(portParam);
        new EchoServer(portParam).start();

    }

    private void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(serverHandler);
                                }
                            });
            ChannelFuture future = server.bind().sync();
            //future.channel().close().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //group.shutdownGracefully().sync();
        }
    }
}
