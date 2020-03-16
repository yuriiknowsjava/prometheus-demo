package edu.yuriikoval1997.prometheusdemo.configs;

import io.prometheus.client.Counter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class YuriiInterceptor extends HandlerInterceptorAdapter {

    private static final Counter counter = Counter.build()
            .name("TOTAL_NUMBER_OF_CALLS")
            .help("Count total number of calls")
            .labelNames("path", "method", "status_code")
            .create()
            .register();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        counter.labels(request.getRequestURI(), request.getMethod(), Integer.toString(response.getStatus()))
                .inc();
    }
}
