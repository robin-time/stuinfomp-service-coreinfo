package com.lxy.stuinfomp.service.coreinfo.controller;

import com.github.pagehelper.PageInfo;
import com.lxy.stuinfomp.commons.domain.Teachers;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.service.TeacherService;
import com.lxy.stuinfomp.commons.validator.BeanValidator;
import com.lxy.stuinfomp.commons.web.AbstractBaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author lxy
 */
@RestController
@RequestMapping(value = "core/teacher")
@Slf4j
public class TeacherController extends AbstractBaseController<Teachers> {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "新增教师信息",notes = "")
    @PostMapping(value = "add")
    public AbstractBaseResult insertTeacherInfo(Teachers teacher){
        Long maxId = teacherService.selectMaxId();
        Long teacherNumber = 100000 + maxId;
        teacher.setTeacherNumber(teacherNumber);
        teacher.setIsDeleted(0);
        Teachers result = teacherService.save(teacher);
        if(null != result){
            response.setStatus(HttpStatus.CREATED.value());
            return success(request.getRequestURI(),result);
        }
        return error("新增失败，请重试",null);
    }

    @ApiOperation(value = "教师信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "笔数", required = true, paramType = "path")
    })
    @GetMapping(value = "page/{pageNum}/{pageSize}")
    public AbstractBaseResult selectTeachers(@ApiParam(name = "教师信息", required = false) Teachers teacher,
                                             @PathVariable int pageNum,
                                             @PathVariable int pageSize){
        try {
            PageInfo<Teachers> pageInfo = teacherService.page(teacher, pageNum, pageSize);
            return success(request.getRequestURI(),pageInfo.getNextPage(),pageInfo.getPages(),pageInfo.getList());
        } catch (Exception e) {
            log.error("TeacherController.selectTeachers()...error = {}",e);
        }
        return error("查询失败，请重试",null);
    }

    @ApiOperation(value = "教师信息更新")
    @PostMapping(value = "updateById")
    public AbstractBaseResult updateTeacherInfo(@ApiParam(name = "教师信息", required = true) Teachers teacher){
        String message = BeanValidator.validator(teacher);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);
        }
        if (teacher.getId() == null){
            return error("修改失败,系统错误","updateTeacherInfo()教师的ID不能为空");
        }
        Teachers result = teacherService.save(teacher);
        if (null != result){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),result);
        }
        return error("修改失败，请重试",null);
    }

    @ApiOperation(value = "逻辑删除教师信息")
    @GetMapping(value = "deleteById/{id}")
    public AbstractBaseResult deleteById(@ApiParam(name = "教师信息",required = true) @PathVariable Long id){
        Teachers result = teacherService.deleteById(new Teachers(), id);
        if (null != result){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),result);
        }
        return error("删除失败，请重试",null);
    }
}
