package cn.lzt.tmall.controller;

import cn.hutool.crypto.digest.DigestUtil;
import cn.lzt.tmall.comparator.*;
import cn.lzt.tmall.po.*;
import cn.lzt.tmall.service.*;
import cn.lzt.tmall.util.AesUtil;
import cn.lzt.tmall.util.Utils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author: lzt
 * @Date: 2019/11/29 21:16
 */
@Controller
public class ForeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/foreHome")
    public String home(Model model) {
        List<Category> categories = categoryService.list(null);
        productService.fill(categories);
        productService.fillByRow(categories);
        model.addAttribute("cs", categories);
        return "fore/home";
    }


    @RequestMapping("/foreRegister")
    @ResponseBody
    public String register(User user) {
        if (userService.isExist(user.getUserName())) {
            return "fail";
        } else {
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
            user.setUserType(0);
            user.setCreateDate(new Date());
            userService.add(user);
            return "success";
        }
    }

    @RequestMapping("/foreLogin")
    @ResponseBody
    public String login(String userName, String password, Model model, HttpSession session) throws Exception {
//        User user = userService.login(AesUtil.decrypt(userName), DigestUtil.md5Hex(AesUtil.decrypt(password)), 0);
        User user = userService.login(AesUtil.decrypt(userName), DigestUtil.md5Hex(AesUtil.decrypt(password)), null);
        if (user != null) {
            session.setAttribute("user", user);
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("/foreLogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/foreHome";
    }

    @RequestMapping("/foreProduct")
    public String product(@RequestParam int pid, Model model) {
        Product product = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.list(pid, ProductImageService.TYPE_SINGLE);
        List<ProductImage> productDetailImages = productImageService.list(pid, ProductImageService.TYPE_DETAIL);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        productService.setSaleAndReviewCount(product);

        List<Review> reviews = reviewService.list(pid);
        List<PropertyValue> pvs = propertyValueService.list(pid);

        model.addAttribute("p", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    @RequestMapping("/foreCheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getUserType() == 0) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/foreCategory")
    public String category(@RequestParam int cid, String sort, Model model) {
        Category category = categoryService.get(cid);
        productService.fill(category);
        productService.setSaleAndReviewCount(category.getProducts());
        if (sort != null) {
            switch (sort) {
                case "all":
                    Collections.sort(category.getProducts(), new ProductAllComparator());
                    break;
                case "review":
                    Collections.sort(category.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(), new ProductPriceComparator());
                    break;
                default:
                    break;
            }
        }
        model.addAttribute("c", category);
        return "fore/category";
    }

    @RequestMapping(value = "/foreSearch", method = RequestMethod.POST)
    public String search(String keyword, Model model) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        model.addAttribute("ps", products);
        return "fore/searchResult";
    }


    @RequestMapping("/foreBuyOne")
    public String buyOne(int pid, int num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //订单项did
        int oiid = 0;
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem oi : orderItems) {
            //购物车里存在该商品的订单项时，增加数量
            if (oi.getPid() == pid) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                oiid = oi.getId();
                found = true;
                break;
            }
        }

        //不存在时，创建一条记录
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setNumber(num);
            oi.setPid(pid);
            oi.setUid(user.getId());
            oi.setStatus(0);
            orderItemService.add(oi);
            oiid = oi.getId();
        }

        //跳转到结算接口
        return "redirect:/foreBuy?oiid=" + oiid;
    }

    /**
     * 结算接口:获取对应的订单项、计算总价
     *
     * @param model
     * @param oiid    直接购买/在购物车选择好商品后进行购买，所以采用数组进行接收
     * @param session
     * @return
     */
    @RequestMapping("/foreBuy")
    public String buy(Model model, int[] oiid, HttpSession session) {
        ArrayList<OrderItem> orderItems = new ArrayList<>(oiid.length);
        float total = 0;
        for (int id : oiid) {
            OrderItem orderItem = orderItemService.get(id);
            orderItems.add(orderItem);
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        }
        //便于在提交订单时，获取已选择的订单项
        session.setAttribute("ois", orderItems);
        model.addAttribute("total", total);
        return "fore/buy";
    }


    @RequestMapping("/foreAddCart")
    @ResponseBody
    public String addCart(int pid, int num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem oi : orderItems) {
            //购物车里存在该商品的订单项时，增加数量
            if (oi.getPid() == pid) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }

        //不存在时，创建一条记录
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setNumber(num);
            oi.setPid(pid);
            oi.setUid(user.getId());
            oi.setStatus(0);
            orderItemService.add(oi);
        }
        return "success";
    }

    @RequestMapping("/foreCart")
    public String cart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", orderItems);
        return "fore/cart";
    }

    @RequestMapping(value = "/foreDeleteOrderItem", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOrderItem(int oiid) {
        orderItemService.delete(oiid);
        return "success";
    }

    @RequestMapping(value = "/foreChangeOrderItem", method = RequestMethod.POST)
    @ResponseBody
    public String changeOrderItem(int pid, int number, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "fail";
        }
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem oi : orderItems) {
            if (oi.getPid() == pid) {
                oi.setNumber(number);
                orderItemService.update(oi);
                break;
            }
        }
        return "success";
    }


    /**
     * 创建订单
     *
     * @param order   订单对象
     * @param session 用户获取勾选的订单项集合
     * @return
     */
    @RequestMapping("/foreCreateOrder")
    public String createOrder(Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");

        //当前时间+4位随机数作为订单码
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.WAITPAY);
        float total = orderService.add(order, orderItems);
        return "redirect:/foreAliPay?oid=" + order.getId() + "&total=" + total;
    }

    @RequestMapping("/foreAliPay")
    public String aliPay() {
        return "fore/alipay";
    }


    @RequestMapping("/forePayed")
    public String payed(Model model, int oid, float total) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.WAITDELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);
        //默认一星期后送达
        Date defaultServiceDate = Utils.addDay(new Date(), 7);

        model.addAttribute("o", order);
        model.addAttribute("total", total);
        model.addAttribute("defaultServiceDate", defaultServiceDate);

        return "fore/payed";
    }

    @RequestMapping("/foreBought")
    public String bought(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        List<Order> orders = orderService.list(user.getId(), OrderService.DELETE);
        model.addAttribute("orders", orders);
        return "fore/bought";
    }


    @RequestMapping("/foreConfirmPay")
    public String confirmPay(Model model, int oid) {
        Order order = orderService.get(oid);
        model.addAttribute("o", order);
        return "fore/confirmPay";
    }

    @RequestMapping("/foreOrderConfirmed")
    public String orderConfirmed(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.WAITREVIEW);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return "fore/orderConfirmed";
    }

    /**
     * 展示该订单下所有的订单项(商品),然后逐个评价
     *
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("/foreReviewList")
    public String reviewList(Model model, int oid) {
        Order order = orderService.get(oid);
        List<OrderItem> orderItems = orderItemService.listByOrderId(oid);
        model.addAttribute("o", order);
        model.addAttribute("ois", orderItems);
        return "fore/reviewList";
    }

    @RequestMapping("/foreReview")
    public String review(Model model, HttpSession session, int id) {

        User user = (User) session.getAttribute("user");
        OrderItem orderItem = orderItemService.get(id);
        Order order = orderService.get(orderItem.getOid());

        Review review = reviewService.getByCondition(user.getId(), orderItem.getPid(), id);

        model.addAttribute("review", review);
        model.addAttribute("oi", orderItem);
        model.addAttribute("o", order);
        return "fore/review";
    }

    @RequestMapping("/foreReviewOne")
    public String reviewOne(Review review, HttpSession session) {
        User user = (User) session.getAttribute("user");
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.add(review);

        OrderItem orderItem = orderItemService.get(review.getOiId());
        //对应的订单项改为已评价
        orderItem.setStatus(1);
        orderItemService.update(orderItem);
        Order order = orderItem.getOrder();

        //1、若订单下不存在未评价的订单项时，修改订单的状态
        List<OrderItem> orderItems = orderItemService.listByCondition(order.getId(), user.getId(), 0);
        if (orderItems.size() == 0) {
            order.setStatus(OrderService.FINISH);
            orderService.update(order);
        }

        return "redirect:/foreReviewList?oid=" + order.getId();
    }

    @RequestMapping(value = "/foreDeleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOrder(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.DELETE);
        orderService.update(order);
        return "success";
    }
}
