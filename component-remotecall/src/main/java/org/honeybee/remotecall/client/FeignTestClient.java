package org.honeybee.remotecall.client;

import cn.hutool.db.Page;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.remotecall.client.fallback.FeignTestClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * openfein测试服务接口
 */
@FeignClient(name = "feignTestClient", url = "${custom.feign.client.test.url}", fallback = FeignTestClientFallback.class)
public interface FeignTestClient {

    @PostMapping("/test/student/query")
    ResponseMessage queryStudentByPage(@RequestBody Page page);

}
