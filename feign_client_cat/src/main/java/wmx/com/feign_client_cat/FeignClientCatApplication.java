package wmx.com.feign_client_cat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @EnableFeignClients：开启 feign 客户端
 * @EnableEurekaClient：开启 eureka 客户端，可以不写，默认就是开启的
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class FeignClientCatApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignClientCatApplication.class, args);
    }

}
