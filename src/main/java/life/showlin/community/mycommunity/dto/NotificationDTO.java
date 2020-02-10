package life.showlin.community.mycommunity.dto;

import life.showlin.community.mycommunity.model.Tuser;
import lombok.Data;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/04 22:12
 *@version 1.0
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerId;
    private String typeName;
    private Integer type;

}
