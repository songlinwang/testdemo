package example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import example.demo.cache.Log;
import example.demo.dto.RequestParams;
import example.demo.meta.User;
import example.demo.service.SenderService;
import example.demo.service.UserService;
import example.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wsl
 * @date 2018/11/23
 */
@RestController
@Slf4j
public class HelloController {

    @Resource
    private UserService userService;

    @Resource
    private SenderService senderService;

    @GetMapping("hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @GetMapping("user/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/rabbit/{param}")

    public ResponseEntity sendMessage(@PathVariable String param) {
        senderService.broadcast(param);
        senderService.direct("某同学过来一下");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log(moduleName = "log", moduleType = 1)
    @GetMapping("test/log")
    @ResponseBody
    public JsonResult printLog(@RequestParam String log) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(200);
        jsonResult.setMsg("成功而来");
        return jsonResult;

    }

    /**
     * get 请求 不可以使用@RequestBody
     * post请求中 form-data、x-www-form-urlencoded 不可以使用 @Requestbody
     * application/json 中json字符串部分可以使用@RequestBody 路径下的必须是用@RequestParam获取
     * @RequestBody
        作用：

        1) 该注解用于读取Request请求的body部分数据，使用系统默认配置的HttpMessageConverter进行解析，然后把相应的数据绑定到要返回的对象上；

        2) 再把HttpMessageConverter返回的对象数据绑定到 controller中方法的参数上。
     *
     * @param log
     * @param
     * @return
     */
    @Log(moduleName = "log", moduleType = 1)
    @PostMapping("test/log/post")
    @ResponseBody

    public JsonResult printPostLog(@RequestParam String log, @RequestParam MultipartFile file, HttpServletRequest request) {

        JsonResult jsonResult = new JsonResult();
        System.out.println("Controller : " + log);
        //System.out.println("Controller : "+ JSONObject.toJSONString(requestParams));
        jsonResult.setCode(200);
        jsonResult.setMsg("成功而来");
        return jsonResult;

    }

    @Log(moduleName = "log", moduleType = 2)
    @PostMapping("test/log/jso")
    @ResponseBody
    public JsonResult printJsonLog(@RequestParam MultipartFile[] file, HttpServletRequest request) {


        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(200);
        jsonResult.setMsg("成功而来");
        return jsonResult;

    }

    @Log(moduleName = "log", moduleType = 2)
    @PostMapping("test/log/common")
    @ResponseBody
    public JsonResult printCommonJsonLog(@RequestParam String param,@RequestParam String test) {

        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(200);
        jsonResult.setMsg("成功而来");
        return jsonResult;

    }
}
