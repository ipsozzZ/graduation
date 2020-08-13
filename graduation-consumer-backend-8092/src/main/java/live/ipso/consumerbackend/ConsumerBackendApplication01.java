package live.ipso.consumerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerBackendApplication01 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerBackendApplication01.class, args);
    }
}
