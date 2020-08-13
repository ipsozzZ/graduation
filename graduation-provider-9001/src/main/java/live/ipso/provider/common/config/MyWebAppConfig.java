package live.ipso.provider.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态文件配置
 */
@Configuration
public class MyWebAppConfig implements WebMvcConfigurer {

    final private static String BASE_ROOT = System.getProperty("user.dir") + "/graduation-provider-9001/src/main/resources/static/images/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + BASE_ROOT);
    }

}
