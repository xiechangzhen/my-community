package life.showlin.community.mycommunity.dto;

import lombok.Data;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/09 23:10
 *@version 1.0
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
