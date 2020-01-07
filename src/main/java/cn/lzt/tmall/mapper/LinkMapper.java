package cn.lzt.tmall.mapper;

import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.EntityMoveService;

import java.util.List;

public interface LinkMapper extends EntityMoveMapper<Link> {
    int deleteByPrimaryKey(Integer id);

    int insert(Link record);

    int insertSelective(Link record);

    List<Link> selectByExample(LinkExample example);

    Link selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Link record);

    int updateByPrimaryKey(Link record);
}