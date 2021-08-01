package netty.gateway.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import netty.gateway.filter.HeaderHttpResponseFilter;
import netty.gateway.filter.HttpRequestFilter;
import netty.gateway.filter.HttpResponseFilter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class HttpOutboundHandler {
    HttpResponseFilter headerHttpResponseFilter = new HeaderHttpResponseFilter();

    public void handle(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx, HttpRequestFilter httpRequestFilter) throws IOException {
        String oriUri = fullHttpRequest.uri(); //获取uri
        oriUri = "/test";

        final String url = "http://127.0.0.1:8081" + oriUri ; //生成新的url
        httpRequestFilter.filter(fullHttpRequest,ctx);//调用请求过滤器
        dispatcher(fullHttpRequest,ctx,url);//转发url
    }

    private void dispatcher(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, String url) throws IOException {
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("requestfrom",fullHttpRequest.headers().get("requestfrom"));
        httpGet.setHeader(HTTP.CONN_DIRECTIVE,HTTP.CONN_KEEP_ALIVE);

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse =  httpClient.execute(httpGet);
        if(httpResponse.getStatusLine().getStatusCode() == 200){

            handleResponse(fullHttpRequest, ctx, httpResponse);
        }
    }

    private void handleResponse(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, HttpResponse httpResponse) {
        FullHttpResponse response = null;
        try {
                byte[] body = EntityUtils.toByteArray(httpResponse.getEntity());
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

                headerHttpResponseFilter.filter(response);
                response.headers().set("Content-Type", "application/json");
                response.headers().setInt("Content-Length", Integer.parseInt(httpResponse.getFirstHeader("Content-Length").getValue()));

            } catch (IOException e) {
                e.printStackTrace();
                response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            }finally {
                if(fullHttpRequest != null){
                    if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                    } else {
                        ctx.write(response);
                    }
                }
            }
        ctx.flush();
        ctx.close();

    }

}
