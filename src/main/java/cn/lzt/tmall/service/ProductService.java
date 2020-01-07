package cn.lzt.tmall.service;

import cn.lzt.tmall.po.Category;
import cn.lzt.tmall.po.Product;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/27 14:12
 */
public interface ProductService extends EntityMoveService<Product>{

    void add(Product p);

    void delete(int id);

    void update(Product p);

    Product get(int id);

    List<Product> list(int cid,Product product);

    /**
     * 为产品设置图片(根据id,取出所有类型为type_single的图片，取第一张)
     *
     * @param p
     */
    void setFirstProductImage(Product p);

    /**
     * 为分类填充商品集合
     *
     * @param category
     */
    void fill(Category category);

    void fill(List<Category> categories);

    /**
     * 为多个分类填充推荐商品集合，一行8个
     *
     * @param categories
     */
    void fillByRow(List<Category> categories);

    void setSaleAndReviewCount(Product product);

    void setSaleAndReviewCount(List<Product> products);

    List<Product> search(String name);
}