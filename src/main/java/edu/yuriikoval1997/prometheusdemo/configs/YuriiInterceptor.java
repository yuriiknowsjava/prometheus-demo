package edu.yuriikoval1997.prometheusdemo.configs;

import io.prometheus.client.Counter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class YuriiInterceptor extends HandlerInterceptorAdapter {

    private final Map<String, Counter> counters = new ConcurrentHashMap<>();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        getCounter(request.getRequestURI())
                .labels(request.getMethod(), Integer.toString(response.getStatus()))
                .inc();
    }

    private Counter getCounter(String name) {
        name = name.replaceAll("/", "_");
        Counter counter = counters.get(name);
        if (counter == null) {
            synchronized (counters) {
                counter = counters.get(name);
                if (counter == null) {
                    counter = Counter.build()
                            .name(name)
                            .help("Count total number of calls")
                            .labelNames("method", "status_code")
                            .create()
                            .register();
                    counters.put(name, counter);
                }
            }
        }
        return counter;
    }
}
