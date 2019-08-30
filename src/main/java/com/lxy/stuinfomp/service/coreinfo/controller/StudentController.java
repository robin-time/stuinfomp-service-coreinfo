package com.lxy.stuinfomp.service.coreinfo.controller;

import com.github.pagehelper.PageInfo;
import com.lxy.stuinfomp.commons.domain.Students;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.service.StudentService;
import com.lxy.stuinfomp.commons.validator.BeanValidator;
import com.lxy.stuinfomp.commons.web.AbstractBaseController;
import com.lxy.stuinfomp.service.coreinfo.requestentity.StudentDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * @author lxy
 */
@RestController
@RequestMapping(value = "core/student")
@Slf4j
public class StudentController extends AbstractBaseController<Students> {

    @Autowired
    private StudentService studentService;


    @ApiOperation(value = "添加学生",notes = "")
    @PostMapping(value = "add")
    public AbstractBaseResult insertStudent(@RequestBody StudentDTO student){
        String message = BeanValidator.validator(student);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);
        }

        try {
            Integer.valueOf(student.getGrade());
        } catch (NumberFormatException e) {
            log.error("StudentController.insertStudent() eror = {}",e);
            return error("入学年级必须是数字类型，例如：1709",null);
        }

        Long maxId = studentService.selectMaxId();
        Long studentId = ++maxId + 1000000;
        Students stu = new Students();
        BeanUtils.copyProperties(student,stu);
        stu.setStudentId(student.getGrade() + studentId);
        stu.setIsDeleted(0);
        Students result = studentService.save(stu);
        if (null != result){
            response.setStatus(HttpStatus.CREATED.value());
            return success(request.getRequestURI(),result);
        }
        return error("新增学生失败，请重试",null);
    }


    @ApiOperation(value = "学生信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "笔数", required = true, paramType = "path")
    })
    @GetMapping(value = "page/{pageNum}/{pageSize}")
    public AbstractBaseResult selectListStudents(@ApiParam(name = "学生信息", required = false) Students student,
                                                 @PathVariable int pageNum,
                                                 @PathVariable int pageSize){
        try {
            PageInfo<Students> studentsPageInfo = studentService.page(student, pageNum, pageSize);
            return success(request.getRequestURI(),studentsPageInfo.getNextPage(),studentsPageInfo.getPages(),studentsPageInfo.getList());
        } catch (Exception e) {
            log.error("StudentController.selectListStudents() error = {}",e);
        }
        return error("查询失败，请重试",null);
    }

    @ApiOperation(value = "学生信息更新")
    @PostMapping(value = "updateById")
    public AbstractBaseResult updateStudentInfo(@ApiParam(name = "学生信息", required = true) Students student){
        String message = BeanValidator.validator(student);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);
        }

        try {
            Integer.valueOf(student.getGrade());
        } catch (NumberFormatException e) {
            log.error("StudentController.updateStudentInfo() eror = {}",e);
            return error("入学年级必须是数字类型，例如：1709",null);
        }
        Students result = studentService.save(student);
        if (null != result){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),result);
        }
        return error("修改失败，请重试",null);
    }


    @ApiOperation(value = "逻辑删除学生信息")
    @GetMapping(value = "deleteById/{id}")
    public AbstractBaseResult deleteById(@ApiParam(name = "学生信息",required = true) @PathVariable Long id){
        Students result = studentService.deleteById(new Students(), id);
        if (null != result){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),result);
        }
        return error("删除失败，请重试",null);
    }

}
