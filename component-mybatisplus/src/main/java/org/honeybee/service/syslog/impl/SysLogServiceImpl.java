package org.honeybee.service.syslog.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.honeybee.entity.syslog.SysLog;
import org.honeybee.mapper.syslog.SysLogMapper;
import org.honeybee.service.syslog.SysLogService;
import org.springframework.stereotype.Service;

/**
 * 系统aop日志表 服务实现类
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
