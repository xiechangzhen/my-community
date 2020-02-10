package life.showlin.community.mycommunity.enums;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/29 23:30
 *@version 1.0
 */

public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论");
    private int type;
    private String name;

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationEnumTypeEnum : NotificationTypeEnum.values()) {
            if (notificationEnumTypeEnum.getType() == type) {
                return notificationEnumTypeEnum.getName();
            }
        }
        return "";
    }
}
