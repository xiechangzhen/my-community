package life.showlin.community.mycommunity.controller;

import life.showlin.community.mycommunity.dto.PaginationDTO;
import life.showlin.community.mycommunity.dto.QuestionDTO;
import life.showlin.community.mycommunity.model.Question;
import life.showlin.community.mycommunity.service.QuestionService;
import life.showlin.community.mycommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yyt
 * @date 2019/9/24 12:32:56
 * @description
 */
@Controller
public class Index {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index( Model model, @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size,
                        @RequestParam(value = "search",required = false) String search){
        PaginationDTO pagination = questionService.list(search, page,size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        return "index";
    }



}
