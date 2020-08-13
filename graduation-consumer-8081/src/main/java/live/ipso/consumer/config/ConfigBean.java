package live.ipso.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration // 标识为配置类
public class ConfigBean {

    /**
     * 创建RestTemplate实例并返回给调用者
     * @return new RestTemplate
     */
    @LoadBalanced  // 负载均衡注解, 可以使用微服务名替换ip地址进行微服务之间的访问
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
