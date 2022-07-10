package com.g.gateway.decorator;

import com.alibaba.cloud.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;

public class HttpResponseDecorator extends ServerHttpResponseDecorator {

    private String proxyUrl;

    private ServerHttpRequest request;

    /**
     * 构造函数
     *
     * @param delegate
     */
    public HttpResponseDecorator(ServerHttpRequest request, ServerHttpResponse delegate, String proxyUrl) {
        super(delegate);
        this.request = request;
        this.proxyUrl = proxyUrl;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        HttpStatus status = this.getStatusCode();
        if (status.equals(HttpStatus.FOUND)) {
            String domain = "";
            if (StringUtils.isBlank(proxyUrl)) {
                domain = request.getURI().getScheme() + "://" + request.getURI().getAuthority(); //这是授权服务的服务名
            } else {
                domain = proxyUrl;
            }
            String location = getHeaders().getFirst("Location");
//            for (int i = 0; i < 3; i++) {
//                location = location.substring(location.indexOf("/") + 1);
//            }
            String replaceLocation = location.replaceAll("^((ht|f)tps?):\\/\\/(\\d{1,3}.){3}\\d{1,3}(:\\d+)?", domain);
            if (location.contains("code=")) {
//                getHeaders().set("Location",location );
            } else {
                getHeaders().set("Location", replaceLocation);
            }
        }
        this.getStatusCode();
        return super.writeWith(body);
    }
}

