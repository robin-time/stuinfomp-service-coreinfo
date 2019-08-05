package com.lxy.stuinfomp.service.coreinfo.controller;

import com.github.pagehelper.PageInfo;
import com.lxy.stuinfomp.commons.domain.Courses;
import com.lxy.stuinfomp.commons.domain.Students;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.service.CourseService;
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
@RequestMapping("core/course")
@Slf4j
public class CourseController extends AbstractBaseController<Courses> {
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "添加课程信息",notes = "")
    @PostMapping(value = "add")
    public AbstractBaseResult insertCourse(@ApiParam(name = "course",value = "课程信息") Courses course){
        String message = BeanValidator.validator(course);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);
        }
        Courses result = courseService.save(course);
        if (null != result){
            response.setStatus(HttpStatus.CREATED.value());
            return success(request.getRequestURI(),result);
        }
        return error("新增课程失败，请重试",null);
    }

    @ApiOperation(value = "课程信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "笔数", required = true, paramType = "path")
    })
    @GetMapping(value = "page/{pageNum}/{pageSize}")
    public AbstractBaseResult selectCourses(@ApiParam(name = "课程信息", required = false) Courses course,
                                                 @PathVariable int pageNum,
                                                 @PathVariable int pageSize){
        try {
            PageInfo<Courses> pageInfo = courseService.page(course, pageNum, pageSize);
            return success(request.getRequestURI(),pageInfo.getNextPage(),pageInfo.getPages(),pageInfo.getList());
        } catch (Exception e) {
            log.error("CourseController.selectCourses() error = {}",e);
        }
        return error("查询失败，请重试",null);
    }

    @ApiOperation(value = "课程信息更新")
    @PostMapping(value = "updateById")
    public AbstractBaseResult updateCourseInfo(@ApiParam(name = "课程信息", required = true) Courses course){
        String message = BeanValidator.validator(course);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);
        }
        Courses result = courseService.save(course);
        if (null != result){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),result);
        }
        return error("修改失败，请重试",null);
    }

    @ApiOperation(value = "逻辑删除课程信息")
    @GetMapping(value = "deleteById/{id}")
    public AbstractBaseResult deleteById(@ApiParam(name = "课程信息",required = true) @PathVariable Long id){
        Courses result = courseService.deleteById(new Courses(), id);
        if (null != result){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),result);
        }
        return error("删除失败，请重试",null);
    }
}
