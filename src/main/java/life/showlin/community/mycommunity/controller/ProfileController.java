package life.showlin.community.mycommunity.controller;

import life.showlin.community.mycommunity.dto.NotificationDTO;
import life.showlin.community.mycommunity.dto.PaginationDTO;
import life.showlin.community.mycommunity.dto.QuestionDTO;
import life.showlin.community.mycommunity.model.Tuser;
import life.showlin.community.mycommunity.service.NotificationService;
import life.showlin.community.mycommunity.service.QuestionService;
import life.showlin.community.mycommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yyt
 * @date 2019/9/26 21:11:44
 * @description
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile")
    public String profile(Model model,HttpServletRequest request,
                          @RequestParam(name = "page" ,defaultValue = "1") Integer page,
                          @RequestParam(name = "action") String action,
                          @RequestParam(name = "size" ,defaultValue = "5") Integer size){
        Tuser user = (Tuser) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if("questions".equals(action)) {
            PaginationDTO<QuestionDTO> pagination = questionService.list(user.getId(),page,size);
            model.addAttribute("pagination", pagination);
            model.addAttribute("actionName","我的提问");
            model.addAttribute("action","questions");
        } else if ("replies".equals(action)) {
            PaginationDTO<NotificationDTO> pagination = notificationService.list(user.getId(), page, size);
            model.addAttribute("pagination", pagination);
            model.addAttribute("actionName","最新回复");
            model.addAttribute("action","replies");
        }
        return "profile";
    }
}
