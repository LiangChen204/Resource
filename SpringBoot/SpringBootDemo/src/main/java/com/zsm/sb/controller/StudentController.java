package com.zsm.sb.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsm.sb.model.ConfigBean;
import com.zsm.sb.model.Student;
import com.zsm.sb.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/4/10.
 * @Modified By:
 */
@RestController
@RequestMapping("/test/")
@EnableAutoConfiguration
@EnableConfigurationProperties({ConfigBean.class})
@Api("SwaggerDemoController 相关API")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @Autowired
    private ConfigBean configBean;

    @ApiOperation(value = "根据username查找", notes = "查询数据库中某个的用户信息")
    @ApiImplicitParam(name = "name", value = "用户名字", paramType = "path", required = true, dataType = "String")
    @RequestMapping("find")
    public String selectTestInfo(String name)
    {
        System.out.println(name);
        System.out.println(configBean.getName());
        return studentService.selectStudentByName(name).toString();
    }

    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(Student student, Model model)
    {
        System.out.println(student);
        System.out.println(configBean.getSecret());

        model.addAttribute("name", student.getUser_name());
        model.addAttribute("password", student.getPassword());
        return "result";
    }

    @ApiIgnore
    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String postRequest(HttpServletRequest request, HttpServletResponse response)
    {
        String data = request.getParameter("data");
        JSONObject json = new JSONObject();
        json.put("data", data);
        json.put("message", "test post request success");

        return json.toString();
    }

    @ApiIgnore
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String getRequest()
    {
        return "test get request success";
    }

    @RequestMapping(value = "toLogin", method = RequestMethod.POST)
    @ResponseBody
    public String SigningIn(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        Student student = new Student();
        student.setUser_name(request.getParameter("userName"));
        student.setPassword(request.getParameter("password"));
        request.getSession().setAttribute("user", student);
        JSONObject json = new JSONObject();
        json.put("result", "1");

        return json.toString();
    }
}
