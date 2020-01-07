package cn.lzt.tmall.mapper;

import cn.lzt.tmall.po.Category;
import cn.lzt.tmall.po.CategoryExample;

import java.util.List;

public interface CategoryMapper extends EntityMoveMapper<Category> {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}