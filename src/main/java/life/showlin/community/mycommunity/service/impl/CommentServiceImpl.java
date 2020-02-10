package life.showlin.community.mycommunity.service.impl;

import life.showlin.community.mycommunity.dto.CommentDTO;
import life.showlin.community.mycommunity.enums.CommentTypeEnum;
import life.showlin.community.mycommunity.enums.NotificationStatusEnum;
import life.showlin.community.mycommunity.enums.NotificationTypeEnum;
import life.showlin.community.mycommunity.exception.CustomizeException;
import life.showlin.community.mycommunity.exception.impl.CustomizeCodeImpl;
import life.showlin.community.mycommunity.mapper.*;
import life.showlin.community.mycommunity.model.*;
import life.showlin.community.mycommunity.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/***
 *
 *@author: XIELIN
 *@date: 2019/11/01 23:17
 *@version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExMapper questionExMapper;
    @Autowired
    private TuserMapper tuserMapper;
    @Autowired
    private CommentExMapper commentExMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    @Override
    public void insert(Comment comment, Tuser commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeCodeImpl.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeCodeImpl.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
           // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeCodeImpl.COMMENT_NOT_FOUND);
            }

            // 问题是否存在
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeCodeImpl.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExMapper.addCommentCount(parentComment);
            // 创建通知
            createNotify(comment, dbComment.getCommentator(),dbComment.getContent(),commentator.getName(), NotificationTypeEnum.REPLY_COMMENT, question.getId());

        }else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeCodeImpl.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExMapper.addCommentCount(question);

            // 创建通知
            createNotify(comment, question.getCreator(),question.getTitle(),commentator.getName(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    @Override
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commontators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>(commontators);

        // 获取评论人并转换为Map
        TuserExample tuserExample = new TuserExample();
        tuserExample.createCriteria().andIdIn(userIds);
        List<Tuser> tusers = tuserMapper.selectByExample(tuserExample);
        Map<Long, Tuser> tusersMap = tusers.stream().collect(Collectors.toMap(t -> t.getId(), t -> t));
        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setTuser(tusersMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOs;
    }

    /**
     * 创建通知
     */
    private void createNotify(Comment comment, Long receiver, String outerTitle, String notifierName, NotificationTypeEnum notificationType,Long outerId) {
        if (String.valueOf(receiver).equals(String.valueOf(comment.getCommentator()))) {
            return ;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setOuterTitle(outerTitle);
        notification.setNotifierName(notifierName);
        notificationMapper.insert(notification);
    }
}
