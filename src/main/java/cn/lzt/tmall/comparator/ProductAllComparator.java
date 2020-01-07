package cn.lzt.tmall.comparator;

import cn.lzt.tmall.po.Product;

import java.util.Comparator;

/**
 * 综合比较器(销量x评价)
 *
 * @Author: lzt
 * @Date: 2019/12/1 20:56
 */
public class ProductAllComparator implements Comparator<Product> {

    /**
     * 1：前者>后者
     * 0：前者=后者
     * -1：前者<后者
     *
     * @param o1 前者
     * @param o2 后者
     * @return
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getReviewCount() * o1.getSaleCount() - o2.getReviewCount() * o2.getSaleCount();
    }
}
