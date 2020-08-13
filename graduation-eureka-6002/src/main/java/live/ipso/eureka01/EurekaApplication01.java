package live.ipso.eureka01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer   // 服务注册中心
@SpringBootApplication
public class EurekaApplication01 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication01.class, args);
    }
}
