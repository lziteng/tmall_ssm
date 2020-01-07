package cn.lzt.tmall.po;

import java.io.Serializable;

public class PropertyValue implements Serializable {
    private static final long serialVersionUID = 3198522808769138787L;
    private Integer id;

    private Integer pid;

    private Integer ptid;

    /**
     * 对应的属性
     */
    private Property property;

    private String value;

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

    public Integer getPtid() {
        return ptid;
    }

    public void setPtid(Integer ptid) {
        this.ptid = ptid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}