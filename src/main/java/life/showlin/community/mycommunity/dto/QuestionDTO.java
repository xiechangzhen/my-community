package life.showlin.community.mycommunity.dto;

import life.showlin.community.mycommunity.model.Tuser;
import lombok.Data;

/**
 * @author yyt
 * @date 2019/9/26 14:05:17
 * @description
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Tuser user;
}
