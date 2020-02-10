package life.showlin.community.mycommunity.exception.impl;

import life.showlin.community.mycommunity.exception.CustomizeCode;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/05 18:45
 *@version 1.0
 */

public enum CustomizeCodeImpl implements CustomizeCode {

    QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不换一个试试"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),

    QUESTION_INSERT_FAILU(2004,"问题新增失败"),
    QUESTION_UPDATE_FAILU(2005,"问题更新失败"),
    TYPE_PARAM_WRONG(2006, "类型参数错误"),
    COMMENT_NOT_FOUND(2007, "回复的问题不存在了"),
    CONTENT_IS_EMPTY(2008,"评论内容不能为空"),
    READ_NOTIFICATION_FAIL(2009,"兄弟你读的是别人的信息呢"),
    NOTIFICATION_NOT_FOUND(2010,"消息不翼而飞了？"),
    FILE_UPLOAD_FAIL(2011,"图片上传失败"),

    REQUIRE_ERROR(4000,"请求错误哦，换一下吧"),
    NO_LOGIN(5000, "未登录，请登录后操作"),
    SYS_ERROR(5001,"服务器冒烟了，请稍后重试!!!");

    private String message;
    private Integer code;

    CustomizeCodeImpl(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
