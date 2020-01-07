package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.PropertyValueMapper;
import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 10:27
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Autowired
    private PropertyService propertyService;

    public PropertyValueServiceImpl() {
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void init(Product p) {
        //根据产品，获取对应分类下的所有属性
        List<Property> properties = propertyService.list(p.getCid(),null);
        for (Property property : properties) {
            //检查产品的该属性是否有对应的属性值记录
            PropertyValue pv = get(property.getId(), p.getId());
            if (pv == null) {
                pv = new PropertyValue();
                pv.setPtid(property.getId());
                pv.setPid(p.getId());
                propertyValueMapper.insertSelective(pv);
            }
        }
    }

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(propertyValueExample);
        if (pvs.isEmpty()) {
            return null;
        }
        return pvs.get(0);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(propertyValueExample);
        for (PropertyValue pv : pvs) {
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        Collections.sort(pvs, new Comparator<PropertyValue>() {
            @Override
            public int compare(PropertyValue o1, PropertyValue o2) {
                return o1.getProperty().getOrderId() - o2.getProperty().getOrderId();
            }
        });
        return pvs;
    }
}
