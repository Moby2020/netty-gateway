package io.geekbang.moby.gateway.server;

import io.geekbang.moby.gateway.router.HttpEndpointRouter;
import io.geekbang.moby.gateway.router.HttpEndpointRouterImpl;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
//import io.netty.handler.codec.http.HttpResponseStatus;
//import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.FullHttpResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class ServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private List<String> endpoints;
    private HttpEndpointRouter endpointRouterImpl;

    public ServerHandler(List<String> endpoints) {
        this.endpoints = endpoints;
        this.endpointRouterImpl = new HttpEndpointRouterImpl();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        String filterMsg = fullHttpRequest.headers().get("nio");
        System.out.println(filterMsg);

        List<Map.Entry<String, String>> entries = fullHttpRequest.headers().entries();
        Map<String, String> map = new HashMap<>();
        entries.forEach(headersMap -> map.put(headersMap.getKey(), headersMap.getValue()));

        Request.Builder builder = new Request.Builder();
        map.forEach(builder::header);

        String url = endpointRouterImpl.route(endpoints);
        String uri = fullHttpRequest.uri();
        builder.url(url + uri);

        Request request = builder.get().build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        byte[] bytes = response.body().bytes();
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytes));
        fullHttpResponse.headers().setInt("Content-Length", bytes.length);
        ctx.writeAndFlush(fullHttpResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
