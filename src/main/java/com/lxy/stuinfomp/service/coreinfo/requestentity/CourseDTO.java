package com.lxy.stuinfomp.service.coreinfo.requestentity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {
    /**
     * 课程名字
     */
    @NotNull(message = "课程名称不能为空")
    private String name;

    /**
     * 教师id
     */
    private Integer tid;

    /**
     * 课程编号
     */
    private Integer courseNumber;
}
