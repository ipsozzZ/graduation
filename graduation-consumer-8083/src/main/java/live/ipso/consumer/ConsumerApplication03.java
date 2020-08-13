package live.ipso.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerApplication03 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication03.class, args);
    }
}
