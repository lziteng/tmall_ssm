package cn.lzt.tmall.service;

import cn.lzt.tmall.po.*;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 10:25
 */
public interface PropertyValueService {

    /**
     * 初始化产品的属性值列表
     *
     * @param p
     */
    void init(Product p);

    void update(PropertyValue pv);

    /**
     * @param ptid 属性id
     * @param pid  产品id
     * @return
     */
    PropertyValue get(int ptid, int pid);

    /**
     * @param pid 产品id
     * @return
     */
    List<PropertyValue> list(int pid);
}