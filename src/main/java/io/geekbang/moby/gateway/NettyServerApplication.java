package io.geekbang.moby.gateway;

import io.geekbang.moby.gateway.server.Server;

/**
 * 程序入口
 */
public class NettyServerApplication {

    public static void main(String[] args) {
        new Server(8989).run();
    }
}
