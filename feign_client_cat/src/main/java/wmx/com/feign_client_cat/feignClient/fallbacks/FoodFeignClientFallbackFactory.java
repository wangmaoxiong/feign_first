package wmx.com.feign_client_cat.feignClient.fallbacks;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wmx.com.feign_client_cat.feignClient.FoodFeignClient;
import wmx.com.feign_client_cat.pojo.Person;

import java.time.LocalDateTime;

/**
 * 1、@FeignClient 注解 fallback 属性值指向的类实现 @FeignClient 接口
 * 2、@FeignClient 注解 fallbackFactory 属性值指向的类实现 FallbackFactory 接口，泛型为 @FeignClient 标注的接口
 * 3、实现 feign.hystrix.FallbackFactory 接口的 create 方法，返回 @FeignClient 标注的接口实现，可以在建新类实现 FoodFeignClient
 * 这里为了简单，直接使用匿名内部类实现.
 * 4、服务回退后备工厂也必须是 Spring 组件.
 */
@Component
public class FoodFeignClientFallbackFactory implements FallbackFactory<FoodFeignClient> {
    private static final Logger logger = LoggerFactory.getLogger(FoodFeignClientFallbackFactory.class);

    /**
     * create 方法相当于在 fallback 方式的基础上封装了一层，用于通过 java.lang.Throwable 获取引起服务回退的原因.
     * create 方法里面与 fallback 方式一致，都是实现 @FeignClient 标注的接口.
     *
     * @param throwable
     * @return
     */
    @Override
    public FoodFeignClient create(Throwable throwable) {
        logger.warn("服务回退原因：" + throwable.getMessage());

        return new FoodFeignClient() {
            @Override
            public String getHunanCuisine(String uuid) {
                JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
                ObjectNode objectNode = jsonNodeFactory.objectNode();
                objectNode.put("code", 1500);
                objectNode.put("param", uuid);
                objectNode.put("message", "服务失败");
                objectNode.put("timestamp", System.currentTimeMillis());
                return objectNode.toString();
            }

            @Override
            public String deleteDataById(Integer id) {
                return null;
            }

            @Override
            public String updateData(String uid, String data) {
                return null;
            }

            @Override
            public String saveData(String jsonData, String type) {
                return null;
            }

            @Override
            public Person updatePerson(Integer pid, String pname, Integer age, LocalDateTime birthday) {
                Person person = new Person();
                person.setPid(10000);
                person.setAge(44);
                person.setPname("弼马温");
                person.setBirthday(LocalDateTime.parse("1011-01-01T11:11:11"));
                return person;
            }

            @Override
            public Person updatePerson2(Integer pid, String pname, Integer age, LocalDateTime birthday) {
                return null;
            }
        };
    }
}
