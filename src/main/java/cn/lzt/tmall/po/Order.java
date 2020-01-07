package cn.lzt.tmall.po;

import cn.lzt.tmall.service.OrderService;

import java.io.Serializable;
import java.util.*;

public class Order implements Serializable {
    private static final long serialVersionUID = -3226837294851356842L;
    private Integer id;

    private String orderCode;

    private String address;

    private String post;

    private String receiver;

    private String mobile;

    private String userMessage;

    private Date createDate;

    /**
     * 支付时间
     */
    private Date payDate;

    /**
     * 发货时间
     */
    private Date deliveryDate;

    /**
     * 收货时间
     */
    private Date confirmDate;

    private Integer uid;

    /**
     * 对应的用户
     */
    private User user;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 订单下的所有订单项
     */
    private List<OrderItem> orderItems;

    /**
     * 总计金额
     */
    private Float totalPrice;

    private int totalNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getStatusDesc() {
        String desc = "";
        switch (getStatus()) {
            case OrderService.WAITPAY:
                desc = "待付款";
                break;
            case OrderService.WAITDELIVERY:
                desc = "待发货";
                break;
            case OrderService.WAITCONFIRM:
                desc = "待收货";
                break;
            case OrderService.WAITREVIEW:
                desc = "待评价";
                break;
            case OrderService.FINISH:
                desc = "完成";
                break;
            case OrderService.DELETE:
                desc = "删除";
                break;
            default:
                break;
        }
        return desc;
    }


}