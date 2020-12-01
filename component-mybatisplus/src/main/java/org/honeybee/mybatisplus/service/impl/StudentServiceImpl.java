package org.honeybee.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.shardingsphere.core.keygen.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.file.util.EasyExcelUtil;
import org.honeybee.mybatisplus.dto.StudentDTO;
import org.honeybee.mybatisplus.dto.StudentQueryDTO;
import org.honeybee.mybatisplus.entity.Student;
import org.honeybee.mybatisplus.mapper.StudentMapper;
import org.honeybee.mybatisplus.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private KeyGenerator keyGenerator;

    @Override
    @Transactional
    public ResultVO saveStudent(StudentDTO student) {
        ResultVO resultVO = new ResultVO();
        resultVO.setFlag(true);

        //判断学生姓名是否重复
//        QueryWrapper queryWrapper = new QueryWrapper<Student>()
//                .eq("name", student.getName());

        //下面等同于上面的写法
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getName, student.getName());
        Student studentOfName = studentMapper.selectOne(queryWrapper);
        if(studentOfName != null) {
            resultVO.setFlag(false);
            resultVO.setMessage("学生姓名已存在");
            return resultVO;
        }

        //同步至数据库
        Student s = new Student();
        s.setUuid(keyGenerator.generateKey().toString());
        BeanUtils.copyProperties(student, s, "id");
        studentMapper.insert(s);

        resultVO.setMessage("新增成功");
        return resultVO;
    }

    @Override
    @Transactional
    public ResultVO updateStudent(StudentDTO student) {
        ResultVO resultVO = new ResultVO();
        resultVO.setFlag(true);
        //判断该学生记录是否存在
        Student s = studentMapper.selectById(student.getId());
        if(s == null) {
            resultVO.setFlag(false);
            resultVO.setMessage("该学生姓名已存在");
            return resultVO;
        }

        //判断学生姓名是否存在
//        QueryWrapper queryWrapper = new QueryWrapper<Student>()
//                .eq("name", student.getName())
//                .ne("id", student.getId());
        //下面等同于上面的写法
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getName, student.getName())
                .ne(Student::getId, student.getId());
        Student stu = studentMapper.selectOne(queryWrapper);
        if(stu != null) {
            resultVO.setFlag(false);
            resultVO.setMessage("该学生姓名已存在");
            return resultVO;
        }

        //同步至数据库
        BeanUtils.copyProperties(student, s, "id");
        studentMapper.updateById(s);

        resultVO.setMessage("更新成功");
        return resultVO;
    }

    @Override
    @Transactional
    public ResultVO batchDeleteStudent(List<Long> ids) {
        ResultVO resultVO = new ResultVO();
        resultVO.setFlag(true);
        resultVO.setMessage("删除成功");

        //更新数据库
        studentMapper.updateDeleteStatusByIds(1, ids);
        return resultVO;
    }

    @Override
    public IPage queryStudentByPage(StudentQueryDTO queryDTO) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<Student>()
                .ge(queryDTO.getMinAge() != null, Student::getAge, queryDTO.getMinAge())
                .le(queryDTO.getMaxAge() != null, Student::getAge, queryDTO.getMaxAge())
                .eq(queryDTO.getIdentity() != null, Student::getIdentity, queryDTO.getIdentity())
                .like(StringUtils.isNotBlank(queryDTO.getName()), Student::getName, queryDTO.getName());

        Page page = new Page(queryDTO.getPageNumber(), queryDTO.getPageSize());
        IPage<Student> result = studentMapper.selectPage(page, queryWrapper);

        return result;
    }

    @Override
    public ResultVO importStudent(MultipartFile file) throws IOException {
        ResultVO resultVO = new ResultVO();
        resultVO.setFlag(true);

        //校验文件类型
        String fileName = file.getOriginalFilename();
        if(!EasyExcelUtil.isExcel2003(fileName) && !EasyExcelUtil.isExcel2007(fileName)) {
            resultVO.setMessage("导入的excel文件格式错误");
            resultVO.setFlag(false);
            return resultVO;
        }
        //获取导入数据
        List<StudentDTO> list = EasyExcelUtil.readSingleExcel(file, new StudentDTO(), 1);
        if(list == null || list.size() < 1) {
            resultVO.setMessage("导入文件为空");
            resultVO.setFlag(false);
            return resultVO;
        }

        StringBuilder sbd = new StringBuilder();
        Boolean flag = true;

        //校验字段信息
        for(int i=0; i<list.size(); i++) {
            StudentDTO dto = list.get(i);
            Boolean checkFlag = true;
            StringBuilder builder = new StringBuilder();

            if(StringUtils.isBlank(dto.getName())) {
                builder.append("姓名为空;");
                checkFlag = false;
            } else {
                if(studentMapper.findByName(dto.getName()) != null) {
                    builder.append("该姓名已存在;");
                    checkFlag = false;
                }
            }
            if(dto.getAge() == null) {
                builder.append("年龄为空;");
                checkFlag = false;
            } else if(dto.getAge() < 1 || dto.getAge() > 255) {
                builder.append("年龄范围应该在(0,255);");
                checkFlag = false;
            }
            if(dto.getIdentity() == null) {
                builder.append("身份为空;");
                checkFlag = false;
            }

            if(!checkFlag) {    //如果校验失败
                flag = false;
                sbd.append("第").append(i+1).append("行数据校验失败:").append(builder).append("\r\n");
            }
        }

        //若校验成功
        if(flag) {
            //执行业务逻辑
            for(StudentDTO dto : list) {
                Student s = new Student();
                BeanUtils.copyProperties(dto, s, "id");
                studentMapper.insert(s);
            }
            resultVO.setMessage("导入成功");
        } else {
            resultVO.setFlag(false);
            resultVO.setMessage(sbd.toString());
        }

        return resultVO;
    }



}
