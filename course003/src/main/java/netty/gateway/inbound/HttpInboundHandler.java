package netty.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import netty.gateway.filter.HeaderHttpRequestFilter;
import netty.gateway.filter.HttpRequestFilter;
import netty.gateway.outbound.HttpOutboundHandler;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpOutboundHandler handler;
    private HttpRequestFilter httpRequestFilter = new HeaderHttpRequestFilter();

    public HttpInboundHandler(){
        this.handler = new HttpOutboundHandler();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;

        handler.handle(fullHttpRequest,ctx,httpRequestFilter);
    }

}
