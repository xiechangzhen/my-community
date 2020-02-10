package life.showlin.community.mycommunity.controller;

import life.showlin.community.mycommunity.dto.CommentDTO;
import life.showlin.community.mycommunity.dto.QuestionDTO;
import life.showlin.community.mycommunity.enums.CommentTypeEnum;
import life.showlin.community.mycommunity.model.Question;
import life.showlin.community.mycommunity.service.CommentService;
import life.showlin.community.mycommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author yyt
 * @date 2019/9/27 14:55:33
 * @description
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model){
        QuestionDTO questionDto = questionService.findById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDto);

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("questionDto",questionDto);
        model.addAttribute("relatedQuestions",relatedQuestions);
        model.addAttribute("comments",comments);
        //增加阅读数
        questionService.addVieCount(id);
        return "question";
    }
}
