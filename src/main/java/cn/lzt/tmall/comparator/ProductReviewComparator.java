package cn.lzt.tmall.comparator;

import cn.lzt.tmall.po.Product;

import java.util.Comparator;

/**
 * @Author: lzt
 * @Date: 2019/12/1 21:07
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getReviewCount() - o2.getReviewCount();
    }
}
