package live.ipso.consumerbackend.controller;

import live.ipso.consumerbackend.config.ProviderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class MessageController_backend {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;
}
