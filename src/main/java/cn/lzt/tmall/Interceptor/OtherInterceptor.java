package cn.lzt.tmall.Interceptor;

import cn.lzt.tmall.po.Category;
import cn.lzt.tmall.po.OrderItem;
import cn.lzt.tmall.po.User;
import cn.lzt.tmall.service.CategoryService;
import cn.lzt.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 初始化分类等信息的拦截器
 *
 * @Author: lzt
 * @Date: 2019/12/2 23:53
 */
public class OtherInterceptor implements HandlerInterceptor {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();
        //获取分类信息，放到搜索栏下面
        List<Category> categories = categoryService.list(null);
        session.setAttribute("cs", categories);

        User user = (User) session.getAttribute("user");
        if (user != null && user.getUserType() == 0) {
            List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
            int cartTotalItemNumber = 0;
            for (OrderItem oi : orderItems) {
                cartTotalItemNumber += oi.getNumber();
            }
            session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
