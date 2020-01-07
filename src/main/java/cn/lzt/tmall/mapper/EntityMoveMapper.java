package cn.lzt.tmall.mapper;

import cn.lzt.tmall.util.OrderIdMap;

/**
 * 可上下移动的mapper
 * <p>
 * 需要在对应的mapper.xml中手动完善getMaxOrderId、preEntityByOrderId、nextEntityByOrderId接口
 *
 * @author lzt
 * @date 2019/12/5 16:47
 */
public interface EntityMoveMapper<T> {

    Integer getMaxOrderId(OrderIdMap orderIdMap);

    T preEntityByOrderId(OrderIdMap orderIdMap);

    T nextEntityByOrderId(OrderIdMap orderIdMap);
}