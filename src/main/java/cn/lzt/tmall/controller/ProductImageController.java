package cn.lzt.tmall.controller;

import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.*;
import cn.lzt.tmall.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @author lzt
 * @date 2019/11/27 16:54
 */
@Controller
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/admin_productImage_list")
    public String list(int pid, Model model) {
        Product p = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.TYPE_SINGLE);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.TYPE_DETAIL);
        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }

    /**
     * 单个图片时，需要删除正常、中等、小号共3张图片
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/admin_productImage_delete")
    public String delete(int id, HttpSession session) {
        ProductImage pi = productImageService.get(id);

        String fileName = pi.getId() + ".jpg";
        String imageFolder;
        //缩略图-小号
        String imageFolder_small = null;
        //缩略图-中等
        String imageFolder_middle = null;

        if (ProductImageService.TYPE_SINGLE.equals(pi.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");

            File imageFile = new File(imageFolder, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            File f_small = new File(imageFolder_small, fileName);
            imageFile.delete();
            f_middle.delete();
            f_small.delete();

        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder, fileName);
            imageFile.delete();
        }

        productImageService.delete(id);
        return "redirect:/admin_productImage_list?pid=" + pi.getPid();
    }

    /**
     * 图片命名格式:id + ".jpg"
     *
     * @param pi                产品图片实体
     * @param session           用于定位存放图片的文件夹路径
     * @param uploadedImageFile 图片文件
     * @return
     */
    @RequestMapping(value = "/admin_productImage_add", method = RequestMethod.POST)
    public String add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) {
        Integer maxOrderId = productImageService.getMaxOrderId(new OrderIdMap(pi.getPid(), null, pi.getType()));
        if (maxOrderId == null) {
            maxOrderId = 0;
        }
        pi.setOrderId(maxOrderId + 1);

        productImageService.add(pi);
        String fileName = pi.getId() + ".jpg";
        String imageFolder;
        //缩略图-小号
        String imageFolder_small = null;
        //缩略图-中等
        String imageFolder_middle = null;

        if (ProductImageService.TYPE_SINGLE.equals(pi.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }

        File f = new File(imageFolder, fileName);
        f.getParentFile().mkdirs();

        try {
            uploadedImageFile.getImage().transferTo(f);
            BufferedImage img = ImageUtil.change2jpg(f);
            ImageIO.write(img, "jpg", f);
            if (ProductImageService.TYPE_SINGLE.equals(pi.getType())) {
                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);
                ImageUtil.resizeImage(f, 56, 56, f_small);
                ImageUtil.resizeImage(f, 217, 190, f_middle);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin_productImage_list?pid=" + pi.getPid();
    }

    /**
     * @param pid       商品id
     * @param id        主键
     * @param type      图片类别
     * @param direction 移动方向
     * @return
     */
    @RequestMapping("/admin_productImage_move")
    @ResponseBody
    public String move(int pid, int id, String type, String direction) {
        ProductImage nowEntity = productImageService.get(id);
        int nowOrderId = nowEntity.getOrderId();
        if ("up".equals(direction)) {
            ProductImage preEntity = productImageService.preEntityByOrderId(new OrderIdMap(pid, nowOrderId, type));
            nowEntity.setOrderId(preEntity.getOrderId());
            preEntity.setOrderId(nowOrderId);
            productImageService.update(nowEntity);
            productImageService.update(preEntity);
        } else if ("down".equals(direction)) {
            ProductImage nextEntity = productImageService.nextEntityByOrderId(new OrderIdMap(pid, nowOrderId, type));
            nowEntity.setOrderId(nextEntity.getOrderId());
            nextEntity.setOrderId(nowOrderId);
            productImageService.update(nowEntity);
            productImageService.update(nextEntity);
        }

        return "success";
    }
}
