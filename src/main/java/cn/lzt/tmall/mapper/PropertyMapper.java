package cn.lzt.tmall.mapper;

import cn.lzt.tmall.po.Property;
import cn.lzt.tmall.po.PropertyExample;
import java.util.List;

public interface PropertyMapper extends EntityMoveMapper<Property> {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}