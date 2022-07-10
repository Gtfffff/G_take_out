package com.g.gateway.filter;

import com.g.gateway.decorator.HttpResponseDecorator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Gtf
 * @Date: 2022/6/11-06-11-0:24
 * @Description: 网上的解决方案，不适配
 * @Version: 1.0
 */
//@Component
public class ResponseGlobalFilter implements GlobalFilter, Ordered {

    //    @Value("${cors.crossOriginPath}")
    private String crossOriginPath = "http://localhost:10000";


    @Override
    public int getOrder() {
        //WRITE_RESPONSE_FILTER 之前执行
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (path.contains("/oauth/authorize") || path.contains("login")) {
            //构建响应包装类
            HttpResponseDecorator responseDecorator = new HttpResponseDecorator(
                    exchange.getRequest(), exchange.getResponse(), crossOriginPath
            );
            return chain
                    .filter(exchange.mutate().response(responseDecorator).build());
        }
        return chain.filter(exchange);
    }
}

