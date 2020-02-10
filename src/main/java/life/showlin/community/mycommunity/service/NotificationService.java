package life.showlin.community.mycommunity.service;

import life.showlin.community.mycommunity.dto.NotificationDTO;
import life.showlin.community.mycommunity.dto.PaginationDTO;
import life.showlin.community.mycommunity.model.Tuser;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/04 22:15
 *@version 1.0
 */

public interface NotificationService {

    /**
     * 查询通知信息分页列表
     * @param userId 用户的主键
     * @param page 当前页数
     * @param size 每页显示数量
     * @return
     */
    PaginationDTO<NotificationDTO> list(Long userId, Integer page, Integer size);

    // 通知数量
    Long unreadCount(Long id);

    NotificationDTO read(Long id, Tuser user);
}
