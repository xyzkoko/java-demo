package cn.com.wind.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * 首页控制器
 * 处理登录、首页等请求
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(HttpServletResponse response, HttpServletRequest request) {
        System.out.println("Session:" + request.getSession());
        System.out.println("Cookie:" + Arrays.toString(request.getCookies()));

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            System.out.println("未登录");
        }
        return "index";
    }

    @RequestMapping("/index2")
    public String index2(HttpServletResponse response, HttpServletRequest request) {
        System.out.println("Session:" + request.getSession());
        System.out.println("Cookie:" + Arrays.toString(request.getCookies()));

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            System.out.println("未登录");
        }

        return "index2";
    }

    @RequestMapping("/login")
    public String login(HttpServletResponse response, HttpServletRequest request) {
        System.out.println("Session:" + request.getSession());
        System.out.println("Cookie:" + Arrays.toString(request.getCookies()));
        return "login";
    }

    @RequestMapping("/callback")
    public String callback(HttpServletResponse response, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
            subject.login(token);
        }
        return "redirect:/index";
    }
}
