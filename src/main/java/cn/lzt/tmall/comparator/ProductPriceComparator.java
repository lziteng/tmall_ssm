package cn.lzt.tmall.comparator;

import cn.lzt.tmall.po.Product;

import java.util.Comparator;

/**
 * @Author: lzt
 * @Date: 2019/12/1 21:10
 */
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return (int) (o1.getPromotePrice() - o2.getPromotePrice());
    }
}
