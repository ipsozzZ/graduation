package live.ipso.consumerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerBackendApplication.class, args);
    }
}
