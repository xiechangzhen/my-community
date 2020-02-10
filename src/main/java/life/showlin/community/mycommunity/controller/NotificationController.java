package life.showlin.community.mycommunity.controller;

import life.showlin.community.mycommunity.dto.NotificationDTO;
import life.showlin.community.mycommunity.enums.NotificationTypeEnum;
import life.showlin.community.mycommunity.model.Tuser;
import life.showlin.community.mycommunity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/06 00:43
 *@version 1.0
 */

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
            @PathVariable(name = "id") Long id) {
        Tuser user = (Tuser) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
