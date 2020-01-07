package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.LinkMapper;
import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.LinkService;
import cn.lzt.tmall.util.OrderIdMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzt
 * @date 2019/12/4 14:53
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    public LinkServiceImpl() {
    }

    @Override
    public void add(Link link) {
        linkMapper.insert(link);
    }

    @Override
    public void delete(int id) {
        linkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        for (Integer id : ids) {
            delete(id);
        }
    }

    @Override
    public void update(Link link) {
        linkMapper.updateByPrimaryKeySelective(link);
    }

    @Override
    public Link get(int id) {
        return linkMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Link> list(Link link) {
        LinkExample linkExample = new LinkExample();
        LinkExample.Criteria criteria = linkExample.createCriteria();
        if (link.getTitle() != null) {
            criteria.andTitleLike("%" + link.getTitle() + "%");
        }
        if (link.getUrl() != null) {
            criteria.andUrlLike("%" + link.getUrl() + "%");
        }
        linkExample.setOrderByClause("orderId");
        List<Link> links = linkMapper.selectByExample(linkExample);
        return links;
    }

    @Override
    public Integer getMaxOrderId(OrderIdMap orderIdMap) {
        return linkMapper.getMaxOrderId(orderIdMap);
    }

    @Override
    public Link preEntityByOrderId(OrderIdMap orderIdMap) {
        return linkMapper.preEntityByOrderId(orderIdMap);
    }

    @Override
    public Link nextEntityByOrderId(OrderIdMap orderIdMap) {
        return linkMapper.nextEntityByOrderId(orderIdMap);
    }
}
