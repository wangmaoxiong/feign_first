package wmx.com.eurekaclient_food.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import wmx.com.eurekaclient_food.pojo.Person;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 菜谱
 *
 * @author wangmaoxiong
 */
@RestController
public class Cuisine {

    private static final Logger logger = LoggerFactory.getLogger(Cuisine.class);

    /**
     * 获取湘菜菜谱数据。访问地址：http://localhost:9395/food/getHunanCuisine?uuid=98389uou8309adko990
     *
     * @return
     */
    @GetMapping("getHunanCuisine")
    public String getHunanCuisine(String uuid) {
        logger.info("获取湖南菜谱,uuid = {}", uuid);
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;//以 json 格式返回
        ArrayNode arrayNode = nodeFactory.arrayNode()
                .add("辣椒炒肉")
                .add("剁椒鱼头")
                .add("蚂蚁上树")
                .add(StringUtils.trimToNull(uuid));
        return arrayNode.toString();
    }

    /**
     * 根据 id 删除：http://localhost:9395/food/deleteDataById?id=980890
     *
     * @param id
     * @return
     */
    @GetMapping("deleteDataById")
    public String deleteDataById(@RequestParam(value = "id") Integer id) {
        logger.info("根据 id 删除,id = {}", id);
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode objectNode = nodeFactory.objectNode();//以 json 格式返回
        objectNode.put("id", id);
        objectNode.put("code", 200);
        objectNode.put("message", "delete success");
        return objectNode.toString();
    }

    /**
     * 更新：http://localhost:9395/food/updateData/889uuo65eud99?data=name_zhangsan,age_33
     *
     * @param uid  ：使用路径变量
     * @param data ：使用普通的请求参数
     * @return
     */
    @RequestMapping(value = "updateData/{uid}", method = RequestMethod.GET)
    public String updateData(@PathVariable("uid") String uid, String data) {
        logger.info("更新数据,uid = {}, data = {}", uid, data);
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode objectNode = nodeFactory.objectNode(); //以 json 格式返回
        objectNode.put("code", 200);
        objectNode.put("message", "更新成功");
        objectNode.put("uid", uid);
        objectNode.put("data", data);
        return objectNode.toString();
    }


    /**
     * 保存数据：http://localhost:9395/food/saveData?type=saveing
     *
     * @param jsonData ：使用请求正文(json 格式)传入，数据在请求体中,如：{"id":9527,"name":"华安","order":100000},页面必须传入
     * @param type     ：使用普通的 key-value 格式传入，数据在请求透中
     * @return
     */
    @PostMapping("saveData")
    public String saveData(@RequestBody String jsonData, @RequestParam String type) {
        logger.info("保存数据,jsonData = {}, type = {}", jsonData, type);
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode objectNode = nodeFactory.objectNode();//以 json 格式返回
        try {
            objectNode.put("code", 200);
            objectNode.put("message", "保存成功");
            objectNode.put("type", type);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            objectNode.set("jsonDta", jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectNode.toString();
    }

    /**
     * 测试 get 方式复杂对象调用：http://localhost:9395/food/updatePerson?pid=100&pname=张三&age=33
     *
     * @param person
     * @return
     */
    @GetMapping("updatePerson")
    public Person updatePerson(Person person) {
        logger.info("更新数据 person = {}", person);
        person.setBirthday(LocalDateTime.now());
        return person;
    }

    /**
     * 测试 post 方式复杂对象调用：http://localhost:9395/food/updatePerson2?pid=100&pname=张三&age=33
     * 1、对于复杂对象，不建议使用 @RequestBody 在请求体中传递数据，因为 feignClient 客户端调用时会很难处理
     * 2、如果非得要使用 @RequestBody ，则建议使用 String 类型，而不是复杂对象
     * 3、也可以如下所示，使用请求头传递参数，这样 feignClient 客户端可以将复杂对象拆成属性调用
     *
     * @param person
     * @return
     */
    @PostMapping("updatePerson2")
    public Person updatePerson2(Person person) {
        logger.info("更新数据(post) person = {}", person);
        person.setBirthday(LocalDateTime.now());
        return person;
    }
}
