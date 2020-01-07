package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.ReviewMapper;
import cn.lzt.tmall.mapper.UserMapper;
import cn.lzt.tmall.po.Review;
import cn.lzt.tmall.po.ReviewExample;
import cn.lzt.tmall.po.User;
import cn.lzt.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lzt
 * @Date: 2019/12/1 14:20
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(Review review) {
        reviewMapper.insertSelective(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        Review review = reviewMapper.selectByPrimaryKey(id);
        setUser(review);
        return review;
    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = reviewMapper.selectByExample(reviewExample);
        setUser(reviews);
        return reviews;
    }

    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }

    @Override
    public Review getByCondition(int uid, int pid, int oiId) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid).andOiIdEqualTo(oiId);
        List<Review> reviews = reviewMapper.selectByExample(reviewExample);
        if (reviews.size() == 0) {
            return null;
        } else {
            Review review = reviews.get(0);
            setUser(review);
            return review;
        }
    }

    private void setUser(List<Review> reviews) {
        for (Review review : reviews) {
            setUser(review);
        }
    }

    private void setUser(Review review) {
        User user = userMapper.selectByPrimaryKey(review.getUid());
        review.setUser(user);
    }
}
