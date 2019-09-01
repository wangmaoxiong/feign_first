package www.com.eurekaserver_changsha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @EnableEurekaServer：开启 eureka 服务
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaserverChangshaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaserverChangshaApplication.class, args);
    }

}
