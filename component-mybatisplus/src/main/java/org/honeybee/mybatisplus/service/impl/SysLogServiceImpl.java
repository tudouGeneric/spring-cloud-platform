package org.honeybee.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.honeybee.mybatisplus.entity.SysLog;
import org.honeybee.mybatisplus.mapper.SysLogMapper;
import org.honeybee.mybatisplus.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * 系统aop日志表 服务实现类
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
