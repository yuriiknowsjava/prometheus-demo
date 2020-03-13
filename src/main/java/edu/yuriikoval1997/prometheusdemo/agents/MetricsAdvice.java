package edu.yuriikoval1997.prometheusdemo.agents;

import io.prometheus.client.Counter;
import net.bytebuddy.asm.Advice;

public class MetricsAdvice {

    public static final Counter COUNTER_ADVICE = Counter
            .build()
            .name("counter_advice")
            .labelNames("path")
            .register();

    @Advice.OnMethodEnter
    public static void before() {
        COUNTER_ADVICE.labels("/**", "*").inc();
    }
}
