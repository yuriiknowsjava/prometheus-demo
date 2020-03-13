package edu.yuriikoval1997.prometheusdemo;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class PrometheusDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PrometheusDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new HTTPServer(8090);
	}

	@Bean
	public Counter helloWorldCounter() {
		return Counter.build()
				.name("hello_total")
				.help("Count total number of calls")
				.labelNames("path", "method", "status_code")
				.create()
				.register();
	}
}
