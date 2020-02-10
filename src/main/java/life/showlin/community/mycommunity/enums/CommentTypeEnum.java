package life.showlin.community.mycommunity.enums;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/29 23:30
 *@version 1.0
 */

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;


    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
