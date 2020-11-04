package io.geekbang.moby.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

public class HttpRequestFilterImpl extends SimpleChannelInboundHandler<FullHttpRequest> implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("nio", "hello, Moby");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        filter(fullHttpRequest, ctx);
        ReferenceCountUtil.retain(fullHttpRequest);
        ctx.fireChannelRead(fullHttpRequest);
    }
}
