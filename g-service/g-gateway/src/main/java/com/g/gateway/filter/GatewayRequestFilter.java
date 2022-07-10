package com.g.gateway.filter;

import com.g.commons.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Gtf
 * @Date: 2022/6/1-06-01-22:20
 * @Description: com.g.gateway.filter
 * @Version: 1.0
 */
@Slf4j
@Component
public class GatewayRequestFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求路径
        String rawPath = exchange.getRequest().getURI().getRawPath();
        //判断请求是否包含私有路径
        if(RequestUtils.isPv(rawPath)){
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN,"can't access private API");
        }
        return chain.filter(exchange);
    }

}
