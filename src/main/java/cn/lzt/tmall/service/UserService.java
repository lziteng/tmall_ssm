package cn.lzt.tmall.service;

import cn.lzt.tmall.po.User;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/28 11:23
 */
public interface UserService {
    void add(User u);

    void delete(int id);

    void update(User u);

    List<User> list();

    User get(int id);

    boolean isExist(String userName);

    User login(String userName, String password, Integer type);
}