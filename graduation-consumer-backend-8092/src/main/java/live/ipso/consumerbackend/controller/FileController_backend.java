package live.ipso.consumerbackend.controller;

import live.ipso.consumerbackend.config.ProviderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class FileController_backend {

    final private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = ProviderConfig.REST_URL_PREFIX;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, Object> upload(MultipartFile file){

        String test = "sss";
        System.out.println(test);
        //对中文格式数据进行处理

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setConnection("Keep-Alive");
        headers.setCacheControl("no-cache");
        HttpEntity<MultipartFile> httpEntity = new HttpEntity<>(file);
        System.out.println(file.toString());
        ResponseEntity<Resource> responseEntity = restTemplate.postForEntity(REST_URL_PREFIX + "file/upload", httpEntity, Resource.class);
        //对读到的内容映射成对应的实体类
        System.out.println(responseEntity.toString());

        return new HashMap<>();
    }
}
