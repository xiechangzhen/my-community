package life.showlin.community.mycommunity.controller;

import life.showlin.community.mycommunity.cache.TagCache;
import life.showlin.community.mycommunity.dto.QuestionDTO;
import life.showlin.community.mycommunity.model.Question;
import life.showlin.community.mycommunity.model.Tuser;
import life.showlin.community.mycommunity.service.QuestionService;
import life.showlin.community.mycommunity.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yyt
 * @date 2019/9/24 12:32:56
 * @description
 */
@Controller
public class PublishController {

    @Autowired
    private UserService iUserService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        QuestionDTO byId = questionService.findById(id);
        if (byId != null) {
            model.addAttribute("id", byId.getId());
            model.addAttribute("description", byId.getDescription());
            model.addAttribute("tag", byId.getTag());
            model.addAttribute("title", byId.getTitle());
            model.addAttribute("tags", TagCache.get());
        }
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        if (title == null || title.equals("")) {
            model.addAttribute("err", "标题不能为空");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("err", "描述不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("err", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("err", "输入非法标签" + invalid);
            return "publish";
        }

        Tuser user = (Tuser) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("err", "您未登录");
            return "publish";
        }

        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);
        return "redirect:/ ";

    }
}
