package cn.lzt.tmall.mapper;

import cn.lzt.tmall.po.Product;
import cn.lzt.tmall.po.ProductExample;
import java.util.List;

public interface ProductMapper extends EntityMoveMapper<Product> {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}