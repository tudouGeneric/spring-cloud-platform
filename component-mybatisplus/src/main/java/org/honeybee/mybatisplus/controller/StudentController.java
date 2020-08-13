package org.honeybee.mybatisplus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.file.util.EasyExcelUtil;
import org.honeybee.file.util.Fileutil;
import org.honeybee.mybatisplus.dto.StudentDTO;
import org.honeybee.mybatisplus.dto.StudentQueryDTO;
import org.honeybee.mybatisplus.entity.Student;
import org.honeybee.mybatisplus.service.StudentService;
import org.honeybee.mybatisplus.valid.group.StudentAddVaildGroup;
import org.honeybee.mybatisplus.valid.group.StudentUpdateValidGroup;
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
        ResultVO resultVO = studentService.saveStudent(student);
        return ResultVO.getResponseMessage(resultVO);
    }

    /**
     * 修改学生
     * @param student
     * @return
     */
    @ApiOperation(value = "修改学生")
    @PutMapping
    public ResponseMessage updateStudent(@RequestBody @Validated(StudentUpdateValidGroup.class) StudentDTO student) {
        ResultVO resultVO = studentService.updateStudent(student);
        return ResultVO.getResponseMessage(resultVO);
    }

    /**
     * 批量删除学生
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除学生")
    @DeleteMapping
    public ResponseMessage deleteStudent(@RequestBody List<Long> ids) {
        ResultVO resultVO = studentService.batchDeleteStudent(ids);
        return ResultVO.getResponseMessage(resultVO);
    }

    /**
     * 分页查询学生
     * @param queryDTO
     * @return
     */
    @PostMapping("/query")
    @ApiOperation(value = "分页查询学生")
    public ResponseMessage queryStudent(@RequestBody StudentQueryDTO queryDTO) {
        IPage result = studentService.queryStudentByPage(queryDTO);
        return ResponseMessage.success("查询成功", result);
    }


    /**
     * 导入学生（单sheet文件导入）
     * @param file
     * @return
     */
    @PostMapping("/import")
    @ApiOperation(value = "导入学生")
    public ResponseMessage importStudent(MultipartFile file) throws IOException {
        ResultVO vo = studentService.importStudent(file);
        return ResultVO.getResponseMessage(vo);
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

    /**
     * 下载导入模板
     */
    @GetMapping("/download/studentTemplate")
    @ApiOperation(value = "下载导入模板")
    public void downloadImportTemplate() throws IOException {
        Fileutil.downloadFile("学生.xlsx", "excel/studentImport.xlsx");
    }

}
