package org.honeybee.mybatisplus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.mybatisplus.dto.StudentDTO;
import org.honeybee.mybatisplus.dto.StudentQueryDTO;
import org.honeybee.mybatisplus.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService extends IService<Student> {

    /**
     * 新增学生
     * @param student
     * @return
     */
    ResultVO saveStudent(StudentDTO student);

    /**
     * 更新学生
     * @param student
     * @return
     */
    ResultVO updateStudent(StudentDTO student);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    ResultVO batchDeleteStudent(List<Long> ids);

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    IPage queryStudentByPage(StudentQueryDTO queryDTO);

    /**
     * 导入学生
     * @param file
     * @return
     * @throws IOException
     */
    ResultVO importStudent(MultipartFile file) throws IOException;

}
