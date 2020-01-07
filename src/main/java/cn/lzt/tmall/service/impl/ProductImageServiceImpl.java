package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.ProductImageMapper;
import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.ProductImageService;
import cn.lzt.tmall.util.OrderIdMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzt
 * @date 2019/11/27 16:08
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageMapper productImageMapper;

    public ProductImageServiceImpl() {
    }

    @Override
    public void add(ProductImage pi) {
        productImageMapper.insertSelective(pi);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageMapper.updateByPrimaryKeySelective(productImage);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductImage> list(int pid, String type) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        productImageExample.setOrderByClause("orderId");
        List<ProductImage> productImages = productImageMapper.selectByExample(productImageExample);

        return productImages;
    }

    @Override
    public Integer getMaxOrderId(OrderIdMap orderIdMap) {
        return productImageMapper.getMaxOrderId(orderIdMap);
    }

    @Override
    public ProductImage preEntityByOrderId(OrderIdMap orderIdMap) {
        return productImageMapper.preEntityByOrderId(orderIdMap);
    }

    @Override
    public ProductImage nextEntityByOrderId(OrderIdMap orderIdMap) {
        return productImageMapper.nextEntityByOrderId(orderIdMap);
    }
}
