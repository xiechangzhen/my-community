package life.showlin.community.mycommunity.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/02 11:43
 *@version 1.0
 */

@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
