package cn.lzt.tmall.controller;

import cn.lzt.tmall.po.Link;
import cn.lzt.tmall.util.OrderIdMap;
import cn.lzt.tmall.service.LinkService;
import cn.lzt.tmall.util.Page;
import com.github.pagehelper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lzt
 * @date 2019/12/4 15:00
 */
@Controller
public class LinkController {

    @Autowired
    private LinkService linkService;

    public LinkController() {
    }

    @RequestMapping("/admin_link_list")
    public String list(Model model, Page page, Link link) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Link> links = linkService.list(link);
        int total = (int) new PageInfo<>(links).getTotal();
        page.setTotal(total);
        model.addAttribute("links", links);
        model.addAttribute("page", page);
        return "admin/listLink";
    }

    @RequestMapping(value = "/admin_link_add", method = RequestMethod.POST)
    public String add(Link link) {
        Integer maxOrderId = linkService.getMaxOrderId(new OrderIdMap(null, null));
        if (maxOrderId == null) {
            maxOrderId = 0;
        }
        link.setOrderId(++maxOrderId);
        linkService.add(link);
        return "redirect:/admin_link_list";
    }


    @RequestMapping("/admin_link_delete")
    public String delete(int id) {
        linkService.delete(id);
        return "redirect:/admin_link_list";
    }

    @RequestMapping(value = "/admin_link_batch_delete", method = RequestMethod.POST)
    public String batchDelete(String[] ids) {

        System.out.println(ids);
        for (String id : ids) {
            System.out.println(id);
        }
        return "redirect:/admin_link_list";
    }

    /**
     * @param id        主键
     * @param direction up/down
     */
    @RequestMapping("/admin_link_move")
    @ResponseBody
    public String move(int id, String direction) {
        Link nowLink = linkService.get(id);
        int nowOrderId = nowLink.getOrderId();
        if ("up".equals(direction)) {
            Link preLink = linkService.preEntityByOrderId(new OrderIdMap(null, nowOrderId));
            nowLink.setOrderId(preLink.getOrderId());
            preLink.setOrderId(nowOrderId);
            linkService.update(nowLink);
            linkService.update(preLink);
        } else if ("down".equals(direction)) {
            Link nextLink = linkService.nextEntityByOrderId(new OrderIdMap(null, nowOrderId));
            nowLink.setOrderId(nextLink.getOrderId());
            nextLink.setOrderId(nowOrderId);
            linkService.update(nowLink);
            linkService.update(nextLink);
        }

        return "success";
    }
}
