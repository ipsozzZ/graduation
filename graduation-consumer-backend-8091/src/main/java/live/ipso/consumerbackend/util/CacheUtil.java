package live.ipso.consumerbackend.util;

import live.ipso.consumerbackend.config.ProviderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * 用于获取redis缓存中的登录的用户信息
 */
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class CacheUtil {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

}
