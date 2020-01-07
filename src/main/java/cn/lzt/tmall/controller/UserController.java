package cn.lzt.tmall.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.lzt.tmall.po.User;
import cn.lzt.tmall.service.UserService;
import cn.lzt.tmall.util.Page;
import com.github.pagehelper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 11:26
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public UserController() {
    }

    @RequestMapping("/admin_user_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> list = userService.list();
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        model.addAttribute("us", list);
        return "admin/listUser";
    }

    @RequestMapping("/admin_user_reset_password")
    @ResponseBody
    public String resetPassword(User u) {
        u.setPassword(DigestUtil.md5Hex("123456"));
        userService.update(u);
        return "success";
    }
}
