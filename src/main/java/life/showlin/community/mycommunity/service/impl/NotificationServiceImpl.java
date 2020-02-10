package life.showlin.community.mycommunity.service.impl;

import life.showlin.community.mycommunity.dto.NotificationDTO;
import life.showlin.community.mycommunity.dto.PaginationDTO;
import life.showlin.community.mycommunity.enums.NotificationStatusEnum;
import life.showlin.community.mycommunity.enums.NotificationTypeEnum;
import life.showlin.community.mycommunity.exception.CustomizeException;
import life.showlin.community.mycommunity.exception.impl.CustomizeCodeImpl;
import life.showlin.community.mycommunity.mapper.NotificationMapper;
import life.showlin.community.mycommunity.mapper.TuserMapper;
import life.showlin.community.mycommunity.model.*;
import life.showlin.community.mycommunity.service.NotificationService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/04 22:38
 *@version 1.0
 */

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private TuserMapper tuserMapper;

    @Override
    public PaginationDTO<NotificationDTO> list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        //得到总条数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.setOrderByClause("gmt_create desc");
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalNum = Integer.valueOf(notificationMapper.countByExample(notificationExample)+"");

        Integer totalPage = totalNum%size >0 ? totalNum/size + 1 : totalNum/size;

        paginationDTO.setTotalPage(totalPage);
        //防止页码超出范围
        page = page > totalPage ? totalPage : page;
        page = page < 1 ? 1 : page;
        //分页查询
        Integer offset = (page - 1) * size;

        //************
        paginationDTO.setPagination(totalPage,page,size);

        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(offset,size));

        if (notifications == null || notifications.size() == 0) {
            return paginationDTO;
        }
//      不用这段代码，直接在Notification中写 谁（名字）回复的 回复的标题名
//        Set<Long> disUserIds = notifications.stream().map(n -> n.getNotifier()).collect(Collectors.toSet());
//        ArrayList<Long> userIds = new ArrayList<>(disUserIds);
//        TuserExample tuserExample = new TuserExample();
//        tuserExample.createCriteria().andIdIn(userIds);
//        List<Tuser> tusers = tuserMapper.selectByExample(tuserExample);
//        Map<Long, Tuser> tuserMap = tusers.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));

        //把notificationDTOS放入分页返回信息中
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification :notifications){
//            Tuser user = userMapper.selectByPrimaryKey(notification.getCreator());
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    @Override
    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    @Override
    public NotificationDTO read(Long id, Tuser user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeCodeImpl.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeCodeImpl.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
