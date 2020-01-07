package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.OrderItemMapper;
import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 15:11
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    public OrderItemServiceImpl() {
    }

    @Override
    public void add(OrderItem o) {
        orderItemMapper.insertSelective(o);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        setOrder(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(orderItemExample);
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    @Override
    public void fill(Order order) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(order.getId());
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        setProduct(orderItems);
        order.setOrderItems(orderItems);

        int totalNumber = 0;
        float totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalNumber += orderItem.getNumber();
            totalPrice += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }

        order.setTotalNumber(totalNumber);
        order.setTotalPrice(totalPrice);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andPidEqualTo(pid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        int saleCount = 0;
        for (OrderItem oi : orderItems) {
            saleCount += oi.getNumber();
        }
        return saleCount;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        setProduct(orderItems);
        return orderItems;
    }

    @Override
    public List<OrderItem> listByOrderId(int oid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(oid);
        orderItemExample.setOrderByClause("id");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        setProduct(orderItems);
        return orderItems;
    }

    @Override
    public List<OrderItem> listByCondition(int oid, int uid, int status) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(oid).andUidEqualTo(uid).andStatusEqualTo(status);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        return orderItems;
    }

    private void setProduct(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            setProduct(orderItem);
        }
    }

    private void setProduct(OrderItem orderItem) {
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }

    private void setOrder(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            setOrder(orderItem);
        }
    }

    private void setOrder(OrderItem orderItem) {
        if (orderItem.getOid() != null) {
            Order order = orderService.get(orderItem.getOid());
            orderItem.setOrder(order);
        }
    }

}
