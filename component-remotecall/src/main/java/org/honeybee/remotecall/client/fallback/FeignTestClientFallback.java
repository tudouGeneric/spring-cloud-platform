package org.honeybee.remotecall.client.fallback;

import cn.hutool.db.Page;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.remotecall.client.FeignTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FeignTestClientFallback implements FeignTestClient {

    @Override
    public ResponseMessage queryStudentByPage(Page page) {
        return ResponseMessage.error("调用失败,服务降级返回", false, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
