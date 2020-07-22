package org.honeybee.mybatisplus.controller;

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
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 新增学生
     * @param student
     * @return
     */
    @PostMapping
    public ResponseMessage saveStudent(@RequestBody @Validated(StudentAddVaildGroup.class) StudentDTO student) {
        Student s = new Student();
        BeanUtils.copyProperties(student, s, "id");
        System.out.println(s.toString());
        studentService.save(s);
        return ResponseMessage.success("新增成功");
    }

}
