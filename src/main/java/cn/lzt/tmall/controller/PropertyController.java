package cn.lzt.tmall.controller;

import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.*;
import cn.lzt.tmall.util.OrderIdMap;
import cn.lzt.tmall.util.Page;
import com.github.pagehelper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/27 11:00
 */
@Controller
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/admin_property_list")
    public String list(int cid, Model model, Page page, Property property) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> ps = propertyService.list(cid, property);
        Category c = categoryService.get(cid);
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        //拼接字符串"&cid="+c.getId()，设置给page对象的Param值。
        //因为属性分页都是基于当前分类下的分页，所以分页的时候需要传递这个cid
        page.setParam("&cid=" + c.getId());

        model.addAttribute("ps", ps);
        model.addAttribute("page", page);
        model.addAttribute("c", c);

        return "admin/listProperty";
    }

    @RequestMapping(value = "/admin_property_add", method = RequestMethod.POST)
    public String add(Property p) {
        Integer maxOrderId = propertyService.getMaxOrderId(new OrderIdMap(p.getCid(), null));
        if (maxOrderId == null) {
            maxOrderId = 0;
        }
        p.setOrderId(maxOrderId + 1);
        propertyService.add(p);
        return "redirect:/admin_property_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_property_delete")
    public String delete(int id) {
        Property p = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:/admin_property_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_property_edit")
    public String edit(int id, Model model) {
        Property p = propertyService.get(id);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        model.addAttribute("p", p);

        return "admin/editProperty";
    }

    @RequestMapping("/admin_property_update")
    public String update(Property p) {
        propertyService.update(p);
        return "redirect:/admin_property_list?cid=" + p.getCid();
    }


    /**
     * @param cid       分类id
     * @param id        主键
     * @param direction
     * @return
     */
    @RequestMapping("/admin_property_move")
    @ResponseBody
    public String move(int cid, int id, String direction) {
        Property nowEntity = propertyService.get(id);
        int nowOrderId = nowEntity.getOrderId();
        if ("up".equals(direction)) {
            Property preEntity = propertyService.preEntityByOrderId(new OrderIdMap(cid, nowOrderId));
            nowEntity.setOrderId(preEntity.getOrderId());
            preEntity.setOrderId(nowOrderId);
            propertyService.update(nowEntity);
            propertyService.update(preEntity);
        } else if ("down".equals(direction)) {
            Property nextEntity = propertyService.nextEntityByOrderId(new OrderIdMap(cid, nowOrderId));
            nowEntity.setOrderId(nextEntity.getOrderId());
            nextEntity.setOrderId(nowOrderId);
            propertyService.update(nowEntity);
            propertyService.update(nextEntity);
        }

        return "success";
    }
}
