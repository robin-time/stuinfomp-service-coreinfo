package com.lxy.stuinfomp.service.coreinfo.requestentity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDTO {

    /**
     * 教师编号:新增教师时候，系统自动生成编号，生成规则：
     *   100000+max（id）的结果值，例如：第100位学生的学号是：100100
     */
    private Long teacherNumber;

    /**
     * 教师姓名
     */
    @NotNull(message = "名字不能为空")
    private String name;

    /**
     * 性别: 0女，1男，2其他
     */
    @NotNull(message = "性别不能为空")
    private String gender;

    private String phone;

    /**
     * 教师的专业
     */
    private String major;

}
