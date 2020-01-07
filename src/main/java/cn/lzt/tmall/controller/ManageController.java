package cn.lzt.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理:用于搭配拦截器进行身份验证
 *
 * @Author: lzt
 * @Date: 2019/11/30 23:54
 */
@Controller
public class ManageController {

    @RequestMapping("/web/admin")
    public String goManagePage() {
        return "redirect:/admin_category_list";
    }
}
