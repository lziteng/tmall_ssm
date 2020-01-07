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

import java.util.*;

/**
 * @author lzt
 * @date 2019/11/27 14:22
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    public ProductController() {
    }

    @RequestMapping("/admin_product_list")
    public String list(int cid, Model model, Page page, Product product) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> products = productService.list(cid, product);
        Category category = categoryService.get(cid);
        int total = (int) new PageInfo<>(products).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + category.getId());

        model.addAttribute("c", category);
        model.addAttribute("ps", products);

        return "admin/listProduct";
    }

    @RequestMapping(value = "/admin_product_add", method = RequestMethod.POST)
    public String add(Product p) {
        Integer maxOrderId = productService.getMaxOrderId(new OrderIdMap(p.getCid(), null));
        if (maxOrderId == null) {
            maxOrderId = 0;
        }
        p.setOrderId(maxOrderId + 1);
        p.setCreateDate(new Date());
        productService.add(p);
        return "redirect:/admin_product_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_product_delete")
    public String delete(int id) {
        Product p = productService.get(id);
        productService.delete(id);
        return "redirect:/admin_product_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_product_edit")
    public String edit(int id, Model model) {
        Product p = productService.get(id);
        model.addAttribute("p", p);
        return "admin/editProduct";
    }

    @RequestMapping(value = "/admin_product_update", method = RequestMethod.POST)
    public String update(Product p) {
        productService.update(p);
        return "redirect:/admin_product_list?cid=" + p.getCid();
    }

    /**
     * @param cid       分类id
     * @param id        主键
     * @param direction
     * @return
     */
    @RequestMapping("/admin_product_move")
    @ResponseBody
    public String move(int cid, int id, String direction) {
        Product nowEntity = productService.get(id);
        int nowOrderId = nowEntity.getOrderId();
        if ("up".equals(direction)) {
            Product preEntity = productService.preEntityByOrderId(new OrderIdMap(cid, nowOrderId));
            nowEntity.setOrderId(preEntity.getOrderId());
            preEntity.setOrderId(nowOrderId);
            productService.update(nowEntity);
            productService.update(preEntity);
        } else if ("down".equals(direction)) {
            Product nextEntity = productService.nextEntityByOrderId(new OrderIdMap(cid, nowOrderId));
            nowEntity.setOrderId(nextEntity.getOrderId());
            nextEntity.setOrderId(nowOrderId);
            productService.update(nowEntity);
            productService.update(nextEntity);
        }

        return "success";
    }
}
