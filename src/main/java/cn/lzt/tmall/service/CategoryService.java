package cn.lzt.tmall.service;

import cn.lzt.tmall.po.Category;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/25 15:16
 */
public interface CategoryService extends EntityMoveService<Category>{
    List<Category> list(Category category);

    void add(Category category);

    void delete(int id);

    Category get(int id);

    void update(Category c);
}