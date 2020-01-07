package cn.lzt.tmall.service;

import cn.lzt.tmall.po.*;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 15:05
 */
public interface OrderItemService {

    void add(OrderItem o);

    void delete(int id);

    void update(OrderItem orderItem);

    OrderItem get(int id);

    List<OrderItem> list();

    void fill(List<Order> orders);

    void fill(Order order);

    /**
     * 根据产品，获取销售量
     *
     * @param pid
     * @return
     */
    int getSaleCount(int pid);

    /**
     * 获取该用户保存在购物车里的所有订单项，用于订单项数量的增减或删除。没有时，就创建一条记录
     *
     * @param uid
     * @return
     */
    List<OrderItem> listByUser(int uid);

    /**
     * 获取订单下所有的订单项，用于逐个进行评价
     *
     * @param oid
     * @return
     */
    List<OrderItem> listByOrderId(int oid);

    /**
     * 根据参数获取对应的订单项集合
     *
     * @param oid    订单id
     * @param uid    用户id
     * @param status 是否评价
     * @return
     */
    List<OrderItem> listByCondition(int oid, int uid, int status);
}