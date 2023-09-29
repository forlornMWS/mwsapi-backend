package xyz.mwszksnmdys.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.mwszksnmdys.apiclientsdk.utils.SignUtil;
import xyz.mwszksnmdys.common.domain.InterfaceInfoDO;
import xyz.mwszksnmdys.common.domain.UserDO;
import xyz.mwszksnmdys.common.service.InnerInterfaceInfoService;
import xyz.mwszksnmdys.common.service.InnerUserInterfaceInfoService;
import xyz.mwszksnmdys.common.service.InnerUserService;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 全局过滤器 类似aop
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    public static final String INTERFACE_HOST = "http://localhost:8090";
    @DubboReference(timeout = 3000)
    private InnerUserService innerUserService;
    @DubboReference(timeout = 3000)
    private InnerInterfaceInfoService innerInterfaceInfoService;


    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    @DubboReference(timeout = 3000)
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    private static Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private static Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 请求日志
        String path = INTERFACE_HOST + request.getPath().value();
        String method = Objects.requireNonNull(request.getMethod()).toString();
        String host = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求唯一标识: {}", request.getId());
        log.info("请求路径: {}", path);
        log.info("请求方法: {}", method);
        log.info("请求参数: {}", request.getQueryParams());
        log.info("请求来源地址: {}", request.getRemoteAddress());
        log.info("请求来源地址: {}", host);
        ServerHttpResponse response = exchange.getResponse();
        // 黑白名单
        if (!IP_WHITE_LIST.contains(host)) {
            return handleNoAuth(response);
        }
        // 鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");
        // 数据库中根据accessKey查询
        UserDO invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error: {}", e.getMessage());
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }
        // todo 随机数存redis
        assert nonce != null;
        if (Long.parseLong(nonce) < 10000) {
            return handleNoAuth(response);
        }
        // timestamp和当前时间不能超过5分钟
        long currentTime = System.currentTimeMillis() / 1000;
        final long FIVE_MINUTES = 60 * 5;
        assert timestamp != null;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
        map.put("nonce", nonce);
        map.put("timestamp", timestamp);
        if (StringUtils.hasText(body)) {
            map.put("body", body);
        }
        String secretKey = invokeUser.getSecretKey();
        String checkSign = SignUtil.generateSign(map, secretKey);
        if (!checkSign.equalsIgnoreCase(sign)) {
            return handleNoAuth(response);
        }
        // 判断模拟接口是否存在: 从数据库中查询请求接口是否存在，以及请求方法是否匹配
        InterfaceInfoDO invokeInterfaceInfo = null;
        try {
            invokeInterfaceInfo = innerInterfaceInfoService.getInvokeInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInvokeInterfaceInfo error: {}", e.getMessage());
        }
        if (invokeInterfaceInfo == null) {
            return handleInvokeError(response);
        }
        //  是否有调用次数
        if (!innerUserInterfaceInfoService.hasInvokeNum(invokeUser.getId(), invokeInterfaceInfo.getId())) {
            return handleInvokeError(response);
        }
        // 请求转发，调用模拟接口 + 响应日志
//        Mono<Void> filter = chain.filter(exchange);
//        return filter;
        return handleResponse(exchange, chain, invokeInterfaceInfo.getId(), invokeUser.getId());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            // 从交换机拿到原始response
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓冲区工厂 拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 对象是响应式的
                        if (body instanceof Flux) {
                            // 我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里面写数据
                            // 拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 调用成功，接口调用次数+1
                                try {
                                    innerUserInterfaceInfoService.invokeInterfaceCount(userId, interfaceInfoId);
                                } catch (Exception e) {
                                    log.error("invokeInterfaceCount error: {}", e.getMessage());
                                }
                                // data从这个content中读取
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);// 释放掉内存
                                // 6.构建日志
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);// data
                                rspArgs.add(data);
                                log.info("<--- status:{} data:{}"// data
                                        , rspArgs.toArray());// log.info("<-- {} {}", originalResponse.getStatusCode(), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 8.调用失败返回错误状态码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);// 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }

    }
}
