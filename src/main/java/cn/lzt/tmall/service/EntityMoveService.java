package cn.lzt.tmall.service;

import cn.lzt.tmall.util.OrderIdMap;

/**
 * 可上下移动接口
 *
 * @author lzt
 * @date 2019/12/5 16:47
 */
public interface EntityMoveService<T> {

    Integer getMaxOrderId(OrderIdMap orderIdMap);

    T preEntityByOrderId(OrderIdMap orderIdMap);

    T nextEntityByOrderId(OrderIdMap orderIdMap);
}