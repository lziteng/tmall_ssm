package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.CategoryMapper;
import cn.lzt.tmall.po.Category;
import cn.lzt.tmall.po.CategoryExample;
import cn.lzt.tmall.util.OrderIdMap;
import cn.lzt.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/25 15:16
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl() {
    }

    @Override
    public List<Category> list(Category category) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (category != null) {
            String name = category.getName();
            if (name != null && !"".equals(name.trim())) {
                criteria.andNameLike("%" + name + "%");
            }
        }

        categoryExample.setOrderByClause("orderId");
        return categoryMapper.selectByExample(categoryExample);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void add(Category category) {
        categoryMapper.insertSelective(category);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Category c) {
        categoryMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Integer getMaxOrderId(OrderIdMap orderIdMap) {
        return categoryMapper.getMaxOrderId(orderIdMap);
    }

    @Override
    public Category preEntityByOrderId(OrderIdMap orderIdMap) {
        return categoryMapper.preEntityByOrderId(orderIdMap);
    }

    @Override
    public Category nextEntityByOrderId(OrderIdMap orderIdMap) {
        return categoryMapper.nextEntityByOrderId(orderIdMap);
    }
}
