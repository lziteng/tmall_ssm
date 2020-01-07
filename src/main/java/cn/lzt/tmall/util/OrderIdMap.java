package cn.lzt.tmall.util;

/**
 * 辅助排序类
 *
 * @Author: lzt
 * @Date: 2019/12/5 23:25
 */
public class OrderIdMap {

    /**
     * 具体实体的id，可为null
     * <p>
     * 例如商品排序时，需要结合cid来查询该类别下的所有商品排序号
     */
    private Integer otherId;

    private Integer orderId;

    /**
     * 其他条件，例如商品图片排序时，需要根据图片类别进行查找(类别:type_single/type_detail)
     */
    private String otherCondition;

    public OrderIdMap(Integer otherId, Integer orderId) {
        this.otherId = otherId;
        this.orderId = orderId;
    }

    public OrderIdMap(Integer otherId, Integer orderId, String otherCondition) {
        this.otherId = otherId;
        this.orderId = orderId;
        this.otherCondition = otherCondition;
    }

    public Integer getOtherId() {
        return otherId;
    }

    public void setOtherId(Integer otherId) {
        this.otherId = otherId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOtherCondition() {
        return otherCondition;
    }

    public void setOtherCondition(String otherCondition) {
        this.otherCondition = otherCondition;
    }
}
