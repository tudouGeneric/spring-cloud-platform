package org.honeybee.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.honeybee.mybatisplus.entity.Student;
import org.honeybee.mybatisplus.mapper.StudentMapper;
import org.honeybee.mybatisplus.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
