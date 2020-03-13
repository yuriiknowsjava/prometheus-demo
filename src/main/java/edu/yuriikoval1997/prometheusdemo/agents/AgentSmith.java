package edu.yuriikoval1997.prometheusdemo.agents;

import edu.yuriikoval1997.prometheusdemo.aspects.YuriiCounted;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.lang.instrument.Instrumentation;

public class AgentSmith {

    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(isAnnotatedWith(named("Watched")))
                .transform(new AgentBuilder.Transformer.ForAdvice()
                        .include(AgentSmith.class.getClassLoader())
                        .advice(named("counters"), MetricsAdvice.class.getName()))
                .installOn(inst);
    }
}
