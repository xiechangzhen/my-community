package life.showlin.community.mycommunity.advice;

import com.alibaba.fastjson.JSON;
import life.showlin.community.mycommunity.dto.ResultDTO;
import life.showlin.community.mycommunity.exception.CustomizeException;
import life.showlin.community.mycommunity.exception.impl.CustomizeCodeImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/***
 *
 *@author: XIELIN
 *@date: 2019/10/05 18:06
 *@version 1.0
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e , Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO = null;
            // 返回json
            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException)e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeCodeImpl.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        } else {
            if (e instanceof CustomizeException){
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeCodeImpl.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }





    }

}
