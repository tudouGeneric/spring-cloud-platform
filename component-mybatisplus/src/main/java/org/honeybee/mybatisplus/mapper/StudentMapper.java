package org.honeybee.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.honeybee.mybatisplus.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 更新删除状态
     * @param deleteStatus
     * @param ids
     */
    void updateDeleteStatusByIds(Integer deleteStatus, List<Long> ids);

}
