package wmx.com.feign_client_cat.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import wmx.com.feign_client_cat.feignClient.fallbacks.FoodFeignClientFallback;
import wmx.com.feign_client_cat.pojo.Person;

import java.time.LocalDateTime;

/**
 * 1、@FeignClient :声明接口为 feign 客户端，value 值为被请求的微服务名称(注册中心可以看到，配置文件中的spring.application.name属性值)，
 * value 可以省略如 @FeignClient("eureka-client-food")，推荐使用 ${} 动态获取配置文件中的值,不用写死.
 * 2、@FeignClient 接口中的方法名称可以自定义，但建议保持与对方一致，请求方式必须一致，请求路径记得带上服务提供方上下文路径(如果有的话)
 * <p>
 * 有些细节需要注意，下面注释中有说明
 */
@FeignClient(name = "${feign.name.food}", fallback = FoodFeignClientFallback.class)
public interface FoodFeignClient {

    /**
     * 获取湘菜菜谱数据
     * 1、"food" 是被请求应用的上下文件路径，一并写在方法上
     * 2、@GetMapping 也可以拆开写成：@RequestMapping(value = "food/getHunanCuisine", method = RequestMethod.GET)
     * 3、参数为字符串时，如果没加 @RequestParam("uuid") 请求参数注解，则请求时会抛异常如下：
     * feign.FeignException: status 405/404 reading FoodFeignClient#getHunanCuisine(String)
     * 4、低版本的 feignClient 方法中不支持 @GetMapping 注解，必须用 @RequestMapping，高版本开始支持 @GetMapping
     *
     * @return
     * @GetMapping("getHunanCuisine")
     */
    @GetMapping("food/getHunanCuisine")
    public String getHunanCuisine(@RequestParam("uuid") String uuid);

    /**
     * 根据 id 删除
     * 1、@RequestMapping 也可以换成 @GetMapping("food/deleteDataById")
     * 2、经过实测参数为整形时，@RequestParam("id") 此时可加可不加，但都建议加上
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "food/deleteDataById", method = RequestMethod.GET)
    public String deleteDataById(@RequestParam("id") Integer id);

    /**
     * 更新
     * 1、高版本的 feignClient 中的 @PathVariable("uid") 注解可以不写 value 属性，如 @PathVariable，低版本则必须写
     * 2、再次提醒：对于 String 参数，服务提供者方法上有没有加 @RequestParam，feignClient 客户端都需要加上，否则调用失败，抛异常：
     * feign.FeignException: status 405 reading FoodFeignClient#updateData(String,String)
     *
     * @param uid  ：使用路径变量
     * @param data ：使用普通的请求参数
     * @return
     */
    @GetMapping(value = "food/updateData/{uid}")
    public String updateData(@PathVariable("uid") String uid, @RequestParam String data);

    /**
     * 保存数据，post 请求。再次提醒 food 是服务提供者应用上下文件路径
     *
     * @return
     */
    @PostMapping("food/saveData")
    public String saveData(@RequestBody String jsonData, @RequestParam String type);

    /**
     * 测试复杂对象调用
     * 1、对于 get 请求，参数为复杂对象时，feignClient 中如果直接使用 public Person updatePerson(Person person); 会抛异常：
     * feign.FeignException: status 404 reading FoodFeignClient#updatePerson(Person)
     * 2、抛异常的原因是对于get方式复杂对象传递时，虽然已经指明了是 get 方式，但是 feign 还是会以 post 方式传递，导致调用失败
     * 3、解决方式1：可以和服务提供者协商，转换成 String 的方式进行传递；解决方式2：将复杂对象(Pojo) 拆成简单的属性，如下所示
     *
     * @return
     */
    @GetMapping("food/updatePerson")
    public Person updatePerson(@RequestParam Integer pid,
                               @RequestParam String pname,
                               @RequestParam Integer age,
                               @RequestParam LocalDateTime birthday);

    /**
     * 测试 post 方式复杂对象调用
     * 1、与 get 方式差不多，当服务提供者使用 updatePerson2(Person person) 时，feign 客户端不能这么直接传递
     * 虽然 post 方式这样直传不会报错，但是对方接收不到数据，所以仍然只能拆成简单的属性进行传递
     * 2、如果对方要求的是 @RequestBody ，则此时这样写会直接抛异常，推荐解决方式是使用 String 类型传递
     *
     * @return
     */
    @PostMapping("food/updatePerson2")
    public Person updatePerson2(@RequestParam Integer pid,
                                @RequestParam String pname,
                                @RequestParam Integer age,
                                @RequestParam LocalDateTime birthday);
}
