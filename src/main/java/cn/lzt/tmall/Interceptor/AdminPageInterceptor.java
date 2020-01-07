package cn.lzt.tmall.Interceptor;

import cn.lzt.tmall.po.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 打开后台管理界面拦截器
 *
 * @Author: lzt
 * @Date: 2019/11/30 23:50
 */
public class AdminPageInterceptor implements HandlerInterceptor {

    //进入Handler方法前执行
    //常用场景:身份认证、身份授权
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //获取请求的url
        String url = request.getRequestURI();

        //打开管理界面时，必须登录且为内网用户
        if (url.indexOf("/web/admin") >= 0) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null || user.getUserType() == 0) {
                response.sendRedirect("/loginPage");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }


    //Controller 方法调用之后，在DispatcherServlet进行视图返回渲染之前被调用，
    //可在此方法中对Controller 处理之后的ModelAndView 对象进行操作
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //在整个请求结束之后:主要作用是用于进行资源清理工作
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
