package com.lxy.stuinfomp.service.coreinfo.controller;

import com.github.pagehelper.PageInfo;
import com.lxy.stuinfomp.commons.domain.Score;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.service.ScoreService;
import com.lxy.stuinfomp.commons.validator.BeanValidator;
import com.lxy.stuinfomp.commons.web.AbstractBaseController;
import com.lxy.stuinfomp.service.coreinfo.requestentity.ScoreDTO;
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

@RestController
@RequestMapping("core/score")
@Slf4j
public class ScoreController extends AbstractBaseController<Score> {

    @Autowired
    private ScoreService scoreService;

    @PostMapping(value = "add")
    public AbstractBaseResult insertScore(@ApiParam(name = "score",value = "成绩信息") @RequestBody ScoreDTO scoreDTO){
        String message = BeanValidator.validator(scoreDTO);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);
        }
        Score score = new Score();
        BeanUtils.copyProperties(scoreDTO,score);
        Score result = scoreService.save(score);
        if (null != result){
            response.setStatus(HttpStatus.CREATED.value());
            return success(request.getRequestURI(),result);
        }
        return error("新增成绩失败，请重试",null);
    }
    
    @ApiOperation(value = "成绩信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "笔数", required = true, paramType = "path")
    })
    @GetMapping(value = "page/{pageNum}/{pageSize}")
    public AbstractBaseResult selectCourses(@ApiParam(name = "成绩信息", required = false) Score score,
                                            @PathVariable int pageNum,
                                            @PathVariable int pageSize){
        try {
            PageInfo<Score> pageInfo = scoreService.page(score, pageNum, pageSize);
            return success(request.getRequestURI(),pageInfo.getNextPage(),pageInfo.getPages(),pageInfo.getList());
        } catch (Exception e) {
            log.error("CourseController.selectCourses() error = {}",e);
        }
        return error("查询失败，请重试",null);
    }

    @ApiOperation(value = "课程信息更新")
    @PostMapping(value = "updateById")
    public AbstractBaseResult updateCourseInfo(@ApiParam(name = "课程信息", required = true) Score score){
        String message = BeanValidator.validator(score);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);
        }
        Score result = scoreService.save(score);
        if (null != result){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),result);
        }
        return error("修改失败，请重试",null);
    }

    @ApiOperation(value = "逻辑删除课程信息")
    @GetMapping(value = "deleteById/{id}")
    public AbstractBaseResult deleteById(@ApiParam(name = "课程信息",required = true) @PathVariable Long id){
        Score result = scoreService.deleteById(new Score(), id);
        if (null != result){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),result);
        }
        return error("删除失败，请重试",null);
    }
}
