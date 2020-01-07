package cn.lzt.tmall.Interceptor;

import cn.lzt.tmall.po.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * 登录认证拦截器
 *
 * @Author: lzt
 * @Date: 2019/12/2 23:35
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //不需要登录就能访问的请求集合
        String[] noNeedAuthPage = new String[]{
                "/registerPage",
                "/loginPage",
                "/foreHome",
                "/foreRegister",
                "/foreLogin",
                "/foreProduct",
                "/foreCheckLogin",
                "/foreCategory",
                "/foreSearch"
        };

        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        if (uri.startsWith("/fore")) {
            //请求不包含在上诉集合中时
            if (!Arrays.asList(noNeedAuthPage).contains(uri)) {
                User user = (User) session.getAttribute("user");
                if (null == user) {
                    //转发到登录接口
                    response.sendRedirect("/loginPage");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
