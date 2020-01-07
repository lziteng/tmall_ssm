package cn.lzt.tmall.service.impl;

import cn.lzt.tmall.mapper.ProductMapper;
import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.*;
import cn.lzt.tmall.util.OrderIdMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzt
 * @date 2019/11/27 14:13
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderItemService orderItemService;

    public ProductServiceImpl() {
    }

    @Override
    public void add(Product p) {
        productMapper.insertSelective(p);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product p) {
        productMapper.updateByPrimaryKeySelective(p);
    }

    @Override
    public Product get(int id) {
        Product p = productMapper.selectByPrimaryKey(id);
        setCategory(p);
        setFirstProductImage(p);
        setSaleAndReviewCount(p);
        return p;
    }

    private void setCategory(Product p) {
        Category category = categoryService.get(p.getCid());
        p.setCategory(category);
    }

    private void setCategory(List<Product> products) {
        for (Product p : products) {
            setCategory(p);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public List<Product> list(int cid, Product product) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        if (product != null) {
            String name = product.getName();
            String subTitle = product.getSubTitle();
            if (name != null && !"".equals(name.trim())) {
                criteria.andNameLike("%" + name + "%");
            }

            if (subTitle != null && !"".equals(subTitle)) {
                criteria.andSubTitleLike("%" + subTitle + "%");
            }
        }
        criteria.andCidEqualTo(cid);
        productExample.setOrderByClause("orderId");
        List<Product> products = productMapper.selectByExample(productExample);
        setCategory(products);
        setFirstProduceImage(products);
        return products;
    }

    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.TYPE_SINGLE);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> products = list(category.getId(), null);
        category.setProducts(products);
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 8;
        for (Category category : categories) {
            List<Product> allProduct = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<List<Product>>(8);
            //使用集合的sublist(index,end)方法进行分行
            for (int i = 0; i < allProduct.size(); i += productNumberEachRow) {
                int endIndex = i + productNumberEachRow;
                endIndex = endIndex > allProduct.size() ? allProduct.size() : endIndex;
                List<Product> productsOfEachRow = allProduct.subList(i, endIndex);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewCount(Product product) {
        int saleCount = orderItemService.getSaleCount(product.getId());
        int reviewCount = reviewService.getCount(product.getId());
        product.setSaleCount(saleCount);
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewCount(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewCount(product);
        }
    }

    @Override
    public List<Product> search(String name) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andNameLike("%" + name + "%");
        List<Product> products = productMapper.selectByExample(productExample);
        setSaleAndReviewCount(products);
        setFirstProduceImage(products);
        return products;
    }

    private void setFirstProduceImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }

    @Override
    public Integer getMaxOrderId(OrderIdMap orderIdMap) {
        return productMapper.getMaxOrderId(orderIdMap);
    }

    @Override
    public Product preEntityByOrderId(OrderIdMap orderIdMap) {
        return productMapper.preEntityByOrderId(orderIdMap);
    }

    @Override
    public Product nextEntityByOrderId(OrderIdMap orderIdMap) {
        return productMapper.nextEntityByOrderId(orderIdMap);
    }
}
