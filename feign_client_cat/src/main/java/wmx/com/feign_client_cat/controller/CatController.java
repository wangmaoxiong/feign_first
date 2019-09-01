package wmx.com.feign_client_cat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import wmx.com.feign_client_cat.feignClient.FoodFeignClient;
import wmx.com.feign_client_cat.pojo.Person;

import javax.annotation.Resource;

@RestController
public class CatController {

    private static final Logger logger = LoggerFactory.getLogger(CatController.class);

    //注入 feign 客户端实例。有了 feign 之后，调用远程微服务就如同调用自己本地的方法一样简单
    //@FeignClient 客户端接口中的方法请求方式要求与服务提供者一致，然而服务消费者自己控制层的调用方式是不受约束的
    @Resource
    private FoodFeignClient foodFeignClient;

    /**
     * 查询湖南菜谱：http://localhost:9394/cat/getHunanCuisine?uuid=77884934euei000pp
     *
     * @param uuid
     * @return
     */
    @GetMapping("getHunanCuisine")
    public String getHunanCuisine(String uuid) {
        logger.info("查询湖南菜谱,uuid = {}", uuid);
        String result = this.foodFeignClient.getHunanCuisine(uuid);
        return result;
    }

    /**
     * 根据 id 删除：http://localhost:9394/cat/deleteDataById?id=980890
     *
     * @param id
     * @return
     */
    @GetMapping("deleteDataById")
    public String deleteDataById(@RequestParam(value = "id") Integer id) {
        logger.info("根据 id 删除,id = {}", id);
        String result = this.foodFeignClient.deleteDataById(id);
        return result;
    }

    /**
     * 更新：http://localhost:9394/cat/updateData/889uuo65eud99?data=name_zhangsan,age_33
     *
     * @param uid  ：使用路径变量
     * @param data ：使用普通的请求参数
     * @return
     */
    @RequestMapping(value = "updateData/{uid}", method = RequestMethod.GET)
    public String updateData(@PathVariable("uid") String uid, String data) {
        logger.info("更新数据,uid = {}, data = {}", uid, data);
        String result = this.foodFeignClient.updateData(uid, data);
        return result;
    }

    /**
     * 保存数据：http://localhost:9394/cat/saveData?type=saveing
     *
     * @param jsonData ：使用请求正文(json 格式)传入，数据在请求体中,如：{"id":9527,"name":"华安","order":100011}
     * @param type     ：使用普通的 key-value 格式传入，数据在请求透中
     * @return
     */
    @PostMapping("saveData")
    public String saveData(@RequestBody String jsonData, @RequestParam String type) {
        logger.info("保存数据,jsonData = {}, type = {}", jsonData, type);
        String result = this.foodFeignClient.saveData(jsonData, type);
        return result;
    }

    /**
     * 测试 get 方式复杂对象调用：http://localhost:9394/cat/updatePerson?pid=100&pname=张三&age=33
     *
     * @param person
     * @return
     */
    @GetMapping("updatePerson")
    public Person updatePerson(Person person) {
        logger.info("updatePerson(get) person = {}", person);
        return this.foodFeignClient.updatePerson(person.getPid(),
                person.getPname(),
                person.getAge(),
                person.getBirthday());
    }

    /**
     * 测试 post 方式复杂对象调用：http://localhost:9394/cat/updatePerson2
     * 1、@RequestBody：表示参数通过请求体传递，且为 json 格式，所以前端必须设置请求类型：Content-Type: application/json
     *
     * @param person :{"pid":9334,"pname":"华雄","age":35}
     * @return
     */
    @PostMapping("updatePerson2")
    public Person updatePerson2(@RequestBody Person person) {
        logger.info("updatePerson(post) person = {}", person);
        return this.foodFeignClient.updatePerson2(person.getPid(),
                person.getPname(),
                person.getAge(),
                person.getBirthday());
    }
}
