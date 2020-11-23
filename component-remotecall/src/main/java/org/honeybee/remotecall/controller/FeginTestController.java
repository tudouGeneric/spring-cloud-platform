package org.honeybee.remotecall.controller;

import cn.hutool.db.Page;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.remotecall.client.FeignTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * feign测试控制类
 */
@RestController
@RequestMapping("/test/feign")
public class FeginTestController {

    @Autowired
    private FeignTestClient feignTestClient;

    @GetMapping("/student/page")
    public ResponseMessage getStudentByPage(@RequestBody Page page) {
        return feignTestClient.queryStudentByPage(page);
    }

}
