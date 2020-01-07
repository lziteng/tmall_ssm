package cn.lzt.tmall.controller;

import cn.lzt.tmall.po.Category;
import cn.lzt.tmall.util.OrderIdMap;
import cn.lzt.tmall.service.CategoryService;
import cn.lzt.tmall.util.*;
import cn.lzt.tmall.util.Page;
import com.github.pagehelper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * 分类管理
 *
 * @author lzt
 * @date 2019/11/25 15:19
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类列表
     * <p>
     * 使用PageHelper实现查询的分页
     *
     * @param model 分类实体
     * @param page  分页bean
     * @return
     * @throws Exception
     */
    @RequestMapping("/admin_category_list")
    public String list(Model model, Page page,Category category) throws Exception {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> list = categoryService.list(category);
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    /**
     * 增加分类
     *
     * @param c         分类实体
     * @param session   获取当前项目的路径，用于保存分类图片文件(属性中不保存该路径，只使用id在指定文件夹内查找id.jgp文件)
     * @param imageFile 图片文件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin_category_add", method = RequestMethod.POST)
    public String add(Category c, HttpSession session, UploadedImageFile imageFile) throws Exception {
        Integer maxOrderId = categoryService.getMaxOrderId(new OrderIdMap(null, null));
        if (maxOrderId == null) {
            maxOrderId = 0;
        }
        c.setOrderId(maxOrderId + 1);

        categoryService.add(c);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //将内存中的文件写入到磁盘中
        imageFile.getImage().transferTo(file);
        BufferedImage image = ImageUtil.change2jpg(file);
        //将image以jpg格式输出到file文件里
        ImageIO.write(image, "jpg", file);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("/admin_category_delete")
    public String delete(int id, HttpSession session) throws Exception {
        categoryService.delete(id);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        return "redirect:/admin_category_list";
    }

    @RequestMapping("/admin_category_edit")
    public ModelAndView edit(int id, ModelAndView mv) throws Exception {
        Category category = categoryService.get(id);
        mv.setViewName("admin/editCategory");
        mv.addObject("category", category);
        return mv;
    }

    @RequestMapping(value = "/admin_category_update", method = RequestMethod.POST)
    public String update(Category c, HttpSession session, UploadedImageFile imageFile) throws Exception {
        categoryService.update(c);
        MultipartFile image = imageFile.getImage();
        if (!image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, c.getId() + ".jpg");
            if (file.exists()) {
                file.delete();
            }
            image.transferTo(file);
            BufferedImage bufferedImage = ImageUtil.change2jpg(file);
            ImageIO.write(bufferedImage, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }

    @ResponseBody
    @RequestMapping("/web/getCategoryList")
    public List<Category> getCategoryList() {
        List<Category> list = categoryService.list(null);
        return list;
    }

    /**
     * @param id        主键
     * @param direction up/down
     */
    @RequestMapping("/admin_category_move")
    @ResponseBody
    public String move(int id, String direction) {
        Category nowEntity = categoryService.get(id);
        int nowOrderId = nowEntity.getOrderId();
        if ("up".equals(direction)) {
            Category preEntity = categoryService.preEntityByOrderId(new OrderIdMap(null, nowOrderId));
            nowEntity.setOrderId(preEntity.getOrderId());
            preEntity.setOrderId(nowOrderId);
            categoryService.update(nowEntity);
            categoryService.update(preEntity);
        } else if ("down".equals(direction)) {
            Category nextEntity = categoryService.nextEntityByOrderId(new OrderIdMap(null, nowOrderId));
            nowEntity.setOrderId(nextEntity.getOrderId());
            nextEntity.setOrderId(nowOrderId);
            categoryService.update(nowEntity);
            categoryService.update(nextEntity);
        }

        return "success";
    }

}
