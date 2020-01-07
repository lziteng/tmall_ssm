package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.PropertyMapper;
import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.PropertyService;
import cn.lzt.tmall.util.OrderIdMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/27 10:53
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    public PropertyServiceImpl() {
    }

    @Override
    public void add(Property p) {
        propertyMapper.insert(p);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Property p) {
        propertyMapper.updateByPrimaryKey(p);
    }

    @Override
    public Property get(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Property> list(int cid, Property property) {
        PropertyExample propertyExample = new PropertyExample();
        PropertyExample.Criteria criteria = propertyExample.createCriteria();
        if (property != null) {
            String name = property.getName();
            if (name != null && !"".equals(name.trim())) {
                criteria.andNameLike("%" + name + "%");
            }
        }

        criteria.andCidEqualTo(cid);
        propertyExample.setOrderByClause("orderId");
        return propertyMapper.selectByExample(propertyExample);
    }

    @Override
    public Integer getMaxOrderId(OrderIdMap orderIdMap) {
        return propertyMapper.getMaxOrderId(orderIdMap);
    }

    @Override
    public Property preEntityByOrderId(OrderIdMap orderIdMap) {
        return propertyMapper.preEntityByOrderId(orderIdMap);
    }

    @Override
    public Property nextEntityByOrderId(OrderIdMap orderIdMap) {
        return propertyMapper.nextEntityByOrderId(orderIdMap);
    }
}
