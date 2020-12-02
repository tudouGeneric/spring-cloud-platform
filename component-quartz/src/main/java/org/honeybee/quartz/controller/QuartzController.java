package org.honeybee.quartz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.quartz.service.QuartzService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quartz")
@Api(tags = "定时任务Quartz相关接口")
@Slf4j
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    @GetMapping("/job")
    @ApiOperation(value = "查询所有定时任务")
    public ResponseMessage quaryAllJob() throws SchedulerException {
        return ResponseMessage.success("success", quartzService.queryAllJob());
    }

}
