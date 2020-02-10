package life.showlin.community.mycommunity.interceptor;

import life.showlin.community.mycommunity.mapper.TuserMapper;
import life.showlin.community.mycommunity.model.Tuser;
import life.showlin.community.mycommunity.model.TuserExample;
import life.showlin.community.mycommunity.service.NotificationService;
import life.showlin.community.mycommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yyt
 * @date 2019/9/27 13:25:29
 * @description
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private TuserMapper tuserMapper;
//    private UserService iUserService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();

                    TuserExample tuserExample = new TuserExample();
                    tuserExample.createCriteria().andTokenEqualTo(token);
                    List<Tuser> tusers = tuserMapper.selectByExample(tuserExample);
//                    Tuser user = iUserService.findByToken(token);
                    if (tusers.size() != 0) {
                        request.getSession().setAttribute("user", tusers.get(0));
                        Long unreadCount = notificationService.unreadCount(tusers.get(0).getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
