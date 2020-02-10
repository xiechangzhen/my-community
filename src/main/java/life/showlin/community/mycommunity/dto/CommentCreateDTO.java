package life.showlin.community.mycommunity.dto;

import lombok.Data;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/06 17:49
 *@version 1.0
 */
@Data
public class CommentCreateDTO {
    private Long id;
    private Long parentId;
    private int type;
    private Long commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;
}
