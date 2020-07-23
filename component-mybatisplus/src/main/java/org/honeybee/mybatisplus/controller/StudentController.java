package org.honeybee.mybatisplus.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.file.util.EasyExcelUtil;
import org.honeybee.mybatisplus.dto.StudentDTO;
import org.honeybee.mybatisplus.entity.Student;
import org.honeybee.mybatisplus.service.StudentService;
import org.honeybee.mybatisplus.valid.group.StudentAddVaildGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 导入学生（单sheet文件导入）
     * @param file
     * @return
     */
    @PostMapping("/import")
    @ApiOperation(value = "导入学生")
    public ResponseMessage importStudent(MultipartFile file) throws IOException {
        //校验文件类型
        String fileName = file.getOriginalFilename();
        if(!EasyExcelUtil.isExcel2003(fileName) && !EasyExcelUtil.isExcel2007(fileName)) {
            return ResponseMessage.error("导入的excel文件格式错误", 500);
        }
        //获取导入数据
        List<StudentDTO> list = EasyExcelUtil.readSingleExcel(file, new StudentDTO(), 1);

        //执行业务逻辑
        for(StudentDTO dto : list) {
            Student s = new Student();
            BeanUtils.copyProperties(dto, s, "id");
            studentService.save(s);
        }
        return ResponseMessage.success("导入成功" + list.size() +"条数据");
    }

    /**
     * 导出学生
     * @throws IOException
     */
    @GetMapping("/export")
    @ApiOperation(value = "导出所有学生")
    public void exportStudent() throws IOException {
        List<Student> list = studentService.list();
        List<StudentDTO> data = new ArrayList<>();
        list.stream().forEach(student -> {
            StudentDTO dto = new StudentDTO();
            BeanUtils.copyProperties(student, dto);
            data.add(dto);
        });
        EasyExcelUtil.writeSingleExcel("学生", "学生", data, StudentDTO.class);
    }

}
