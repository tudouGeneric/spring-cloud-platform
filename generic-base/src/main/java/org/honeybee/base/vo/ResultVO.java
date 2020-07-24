package org.honeybee.base.vo;

import lombok.Data;
import org.honeybee.base.common.ResponseMessage;

@Data
public class ResultVO {

    private Boolean flag;

    private String message;

    public ResultVO() {
    }

    public ResultVO(Boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public static ResponseMessage getResponseMessage(ResultVO vo) {
        if(vo.getFlag()) {
            return ResponseMessage.success(vo.getMessage());
        } else {
            return ResponseMessage.fail(vo.getMessage());
        }
    }

}
