package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.UserMapper;
import cn.lzt.tmall.po.User;
import cn.lzt.tmall.po.UserExample;
import cn.lzt.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 11:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public UserServiceImpl() {
    }

    @Override
    public void add(User u) {
        userMapper.insertSelective(u);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User u) {
        userMapper.updateByPrimaryKeySelective(u);
    }

    @Override
    public List<User> list() {
        return userMapper.selectByExample(null);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean isExist(String userName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(userExample);
        return !users.isEmpty();
    }

    @Override
    public User login(String userName, String password, Integer userType) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(password);
        if(null != userType){
            userExample.createCriteria().andUserTypeEqualTo(userType);
        }

        List<User> users = userMapper.selectByExample(userExample);
        return users.isEmpty() ? null : users.get(0);
    }
}
