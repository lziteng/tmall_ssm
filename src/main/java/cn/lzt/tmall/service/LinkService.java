package cn.lzt.tmall.service;

import cn.lzt.tmall.po.Link;

import java.util.List;

/**
 * @author lzt
 * @date 2019/12/4 14:41
 */
public interface LinkService extends EntityMoveService<Link> {
    void add(Link link);

    void delete(int id);

    void deleteByIds(List<Integer> ids);

    void update(Link link);

    Link get(int id);

    List<Link> list(Link link);
}