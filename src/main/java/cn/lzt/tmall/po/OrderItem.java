package cn.lzt.tmall.po;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private static final long serialVersionUID = 4642765696915944278L;

    private Integer id;

    private Integer pid;

    /**
     * 对应的产品
     */
    private Product product;

    private Integer oid;

    /**
     * 对应的订单
     */
    private Order order;

    private Integer uid;

    private Integer number;

    /**
     * 是否已评价
     * <p>
     * 0：未评价
     * 1：已评价
     */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}