package com.lxy.stuinfomp.service.coreinfo.controller;

import com.lxy.stuinfomp.commons.domain.Teachers;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.service.TeacherService;
import com.lxy.stuinfomp.commons.web.AbstractBaseController;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Teachers result = teacherService.save(teacher);
        if(null != result){
            response.setStatus(HttpStatus.CREATED.value());
            return success(request.getRequestURI(),result);
        }
        return error("新增失败，请重试",null);
    }

}
