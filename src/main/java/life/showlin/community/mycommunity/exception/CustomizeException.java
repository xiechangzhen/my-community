package life.showlin.community.mycommunity.exception;

import lombok.Getter;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/05 18:44
 *@version 1.0
 */
@Getter
public class CustomizeException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomizeException(CustomizeCode customizeCode) {
        this.code = customizeCode.getCode();
        this.message = customizeCode.getMessage();
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
