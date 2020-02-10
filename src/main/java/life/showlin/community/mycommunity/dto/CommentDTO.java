package life.showlin.community.mycommunity.dto;

import life.showlin.community.mycommunity.model.Tuser;
import lombok.Data;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/06 17:49
 *@version 1.0
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private int type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Tuser tuser;
    private Integer commentCount;

}
