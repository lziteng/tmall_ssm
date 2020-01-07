package cn.lzt.tmall.po;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
    private static final long serialVersionUID = -3281133729923056860L;
    private Integer id;

    private String content;

    private Integer uid;

    /**
     * 对应的用户
     */
    private User user;

    private Integer pid;

    private Date createDate;

    /**
     * 所属的订单项
     */
    private Integer oiId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getOiId() {
        return oiId;
    }

    public void setOiId(Integer oiId) {
        this.oiId = oiId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}