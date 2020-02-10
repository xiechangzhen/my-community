package life.showlin.community.mycommunity.service;

import life.showlin.community.mycommunity.dto.CommentCreateDTO;
import life.showlin.community.mycommunity.dto.CommentDTO;
import life.showlin.community.mycommunity.enums.CommentTypeEnum;
import life.showlin.community.mycommunity.model.Comment;
import life.showlin.community.mycommunity.model.Tuser;

import java.util.List;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/29 23:45
 *@version 1.0
 */

public interface CommentService {

    // 新增评论或回复
    void insert(Comment comment, Tuser commentator);

    /**
     * 评论列表u
     * @param id
     * @return
             */
    List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type);
}
