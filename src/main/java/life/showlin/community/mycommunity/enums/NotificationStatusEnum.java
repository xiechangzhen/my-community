package life.showlin.community.mycommunity.enums;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/05 01:07
 *@version 1.0
 */

public enum NotificationStatusEnum {
    UNREAD(0),READ(1);

    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

}
