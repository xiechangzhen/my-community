package life.showlin.community.mycommunity.controller;

import life.showlin.community.mycommunity.dto.CommentCreateDTO;
import life.showlin.community.mycommunity.dto.CommentDTO;
import life.showlin.community.mycommunity.dto.ResultDTO;
import life.showlin.community.mycommunity.enums.CommentTypeEnum;
import life.showlin.community.mycommunity.exception.impl.CustomizeCodeImpl;
import life.showlin.community.mycommunity.model.Comment;
import life.showlin.community.mycommunity.model.Tuser;
import life.showlin.community.mycommunity.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/06 17:43
 *@version 1.0
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public Object handle(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
        Comment comment = new Comment();
        Tuser tuser = (Tuser) request.getSession().getAttribute("user");
        if (tuser == null) {
            return ResultDTO.errorOf(CustomizeCodeImpl.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeCodeImpl.CONTENT_IS_EMPTY);
        }
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(tuser.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment, tuser);
        return ResultDTO.okOf();
    }

    @GetMapping("/comment/{id}")
    public ResultDTO<List> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}