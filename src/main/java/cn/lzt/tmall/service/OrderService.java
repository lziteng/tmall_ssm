package cn.lzt.tmall.service;

import cn.lzt.tmall.po.Order;
import cn.lzt.tmall.po.OrderItem;
import org.aspectj.weaver.ast.Or;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 15:16
 */
public interface OrderService {
    /**
     * 待付款
     */
    String WAITPAY = "waitPay";

    /**
     * 待发货
     */
    String WAITDELIVERY = "waitDelivery";

    /**
     * 待收货
     */
    String WAITCONFIRM = "waitConfirm";

    /**
     * 待评价
     */
    String WAITREVIEW = "waitReview";

    /**
     * 完成
     */
    String FINISH = "finish";

    /**
     * 删除
     */
    String DELETE = "delete";

    void add(Order c);

    void delete(int id);

    void update(Order c);

    Order get(int id);

    List<Order> list();

    /**
     * 根据订单项，生成订单，同时为订单项设置对应的oid
     *
     * @param order      订单
     * @param orderItems 订单项集合
     * @return
     */
    float add(Order order, List<OrderItem> orderItems);


    /**
     * 查询用户下，排除状态外的所有订单
     *
     * @param uid            用户id
     * @param excludedStatus 排除状态
     * @return
     */
    List<Order> list(int uid, String excludedStatus);
}