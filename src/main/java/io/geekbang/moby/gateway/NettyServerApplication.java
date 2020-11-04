package io.geekbang.moby.gateway;

import io.geekbang.moby.gateway.server.Server;

import java.util.ArrayList;

/**
 * 程序入口
 */
public class NettyServerApplication {

    private final static ArrayList<String> PROXY_SERVER_LIST = new ArrayList<>();

    static {
        PROXY_SERVER_LIST.add("http://localhost:8088");
        PROXY_SERVER_LIST.add("http://localhost:8089");
        PROXY_SERVER_LIST.add("http://localhost:8090");
    }

    public static void main(String[] args) {
        int port = 8989;
        System.out.println("开启 netty server, started at: http://localhost:" + port);
        new Server(PROXY_SERVER_LIST, port).run();
    }
}
