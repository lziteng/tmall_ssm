package cn.lzt.tmall.mapper;

import cn.lzt.tmall.po.ProductImage;
import cn.lzt.tmall.po.ProductImageExample;
import java.util.List;

public interface ProductImageMapper extends EntityMoveMapper<ProductImage> {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}