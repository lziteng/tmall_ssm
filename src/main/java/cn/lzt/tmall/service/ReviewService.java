package cn.lzt.tmall.service;

import cn.lzt.tmall.po.Review;

import java.util.List;

/**
 * @Author: lzt
 * @Date: 2019/12/1 14:18
 */
public interface ReviewService {
    void add(Review review);

    void delete(int id);

    void update(Review review);

    Review get(int id);

    List<Review> list(int pid);

    int getCount(int pid);

    /**
     * 根据条件查询评价信息
     *
     * @param uid  用户id
     * @param pid  商品id
     * @param oiId 订单项id
     * @return
     */
    Review getByCondition(int uid, int pid, int oiId);
}
