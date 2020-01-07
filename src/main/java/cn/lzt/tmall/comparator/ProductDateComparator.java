package cn.lzt.tmall.comparator;

import cn.lzt.tmall.po.Product;

import java.util.Comparator;

/**
 * @Author: lzt
 * @Date: 2019/12/1 21:08
 */
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getCreateDate().compareTo(o1.getCreateDate());
    }
}
