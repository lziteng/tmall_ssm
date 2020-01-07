package cn.lzt.tmall.service;

import cn.lzt.tmall.po.ProductImage;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/27 16:06
 */
public interface ProductImageService extends EntityMoveService<ProductImage> {
    String TYPE_SINGLE = "type_single";
    String TYPE_DETAIL = "type_detail";

    void add(ProductImage pi);

    void delete(int id);

    void update(ProductImage productImage);

    ProductImage get(int id);

    /**
     * @param pid
     * @param type 类别:type_single/type_detail
     * @return
     */
    List<ProductImage> list(int pid, String type);
}