package cn.lzt.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理前端页面间的跳转逻辑
 *
 * @Author: lzt
 * @Date: 2019/11/30 0:29
 */
@Controller
public class PageController {

    @RequestMapping("/registerPage")
    public String registerPage() {
        return "fore/register";
    }

    @RequestMapping("/registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "fore/login";
    }
}
