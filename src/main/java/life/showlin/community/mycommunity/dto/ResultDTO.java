package life.showlin.community.mycommunity.dto;

import life.showlin.community.mycommunity.exception.CustomizeException;
import life.showlin.community.mycommunity.exception.impl.CustomizeCodeImpl;
import lombok.Data;

/***
 *
 *@author: XIELIN
 *@date: 2019/10/29 23:19
 *@version 1.0
 */
@Data
public class ResultDTO<T> {

    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeCodeImpl customizeCode) {
        return errorOf(customizeCode.getCode(), customizeCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("返回成功");
        return resultDTO;
    }

    public static <T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("返回成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
