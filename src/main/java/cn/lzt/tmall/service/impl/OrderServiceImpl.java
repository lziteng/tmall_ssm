package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.OrderMapper;
import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 15:18
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemService orderItemService;

    public OrderServiceImpl() {
    }

    @Override
    public void add(Order c) {
        orderMapper.insertSelective(c);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order c) {
        orderMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Order get(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        orderItemService.fill(order);
        return order;
    }

    @Override
    public List<Order> list() {
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(orderExample);
        setUser(orders);
        return orders;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        add(order);
        //模拟异常，检测事务控制是否有效
//        if (true)
//            throw new RuntimeException();

        for (OrderItem oi : orderItems) {
            oi.setOid(order.getId());
            orderItemService.update(oi);
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
        }
        return total;
    }

    @Override
    public List<Order> list(int uid, String excludedStatus) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        orderItemService.fill(orders);
        return orders;
    }

    private void setUser(List<Order> orders) {
        for (Order order : orders) {
            setUser(order);
        }
    }

    private void setUser(Order order) {
        User user = userService.get(order.getUid());
        order.setUser(user);
    }
}
