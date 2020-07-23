package org.honeybee.mybatisplus.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.mybatisplus.dto.StudentDTO;
import org.honeybee.mybatisplus.entity.Student;
import org.honeybee.mybatisplus.service.StudentService;
import org.honeybee.mybatisplus.valid.group.StudentAddVaildGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/student")
@Slf4j
@Api(tags = "Student测试功能接口")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 新增学生
     * @param student
     * @return
     */
    @ApiOperation(value = "新增学生")
    @PostMapping
    public ResponseMessage saveStudent(@RequestBody @Validated(StudentAddVaildGroup.class) StudentDTO student) {
        Student s = new Student();
        BeanUtils.copyProperties(student, s, "id");
        log.info(s.toString());
        studentService.save(s);
        return ResponseMessage.success("新增成功");
    }

}
