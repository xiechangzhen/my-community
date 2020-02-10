package life.showlin.community.mycommunity.interceptor;

import life.showlin.community.mycommunity.controller.FileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yyt
 * @date 2019/9/27 13:22:05
 * @description
 */
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
        //registry.addInterceptor(new ThemeInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
        //registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadImage/**").addResourceLocations("file:" + "D:\\idea\\workspace\\my-community\\src\\main\\resources\\static\\uploadImage\\");
    }
}