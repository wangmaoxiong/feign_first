package wmx.com.feign_client_cat.feignClient.fallbacks;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import wmx.com.feign_client_cat.feignClient.FoodFeignClient;
import wmx.com.feign_client_cat.pojo.Person;

import java.time.LocalDateTime;

/**
 * @FeignClient fallback 回退实现类必须是 spring 组件，比如 @Service、@Repository、@Component 等
 * fallback 服务回退实现类实现 @FeignClient 接口.
 */
@Component
@SuppressWarnings("all")
public class FoodFeignClientFallback implements FoodFeignClient {
    @Override
    public String getHunanCuisine(String uuid) {
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode objectNode = jsonNodeFactory.objectNode();
        objectNode.put("code", 500);
        objectNode.put("param", uuid);
        objectNode.put("message", "服务故障");
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
        person.setPid(9527);
        person.setAge(33);
        person.setPname("华安");
        person.setBirthday(LocalDateTime.parse("1987-09-09T12:12:00"));
        return person;
    }

    @Override
    public Person updatePerson2(Integer pid, String pname, Integer age, LocalDateTime birthday) {
        return null;
    }
}
