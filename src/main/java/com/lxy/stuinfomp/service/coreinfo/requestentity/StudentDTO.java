package com.lxy.stuinfomp.service.coreinfo.requestentity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author lxy
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO  {
    /**
     * 学号,新增时系统生成学号，规则：grade的信息 连接 100000+max（id）的结果值；例如1号学生学号
     * 1709100001 共计9位数字组成每届学生目前最大人数100万-1，第100位学生的学号是：1709100100
     */
    private String studentId;

    /**
     * 学生姓名
     */
    @NotNull(message = "学生姓名不能为空")
    @Length(min = 3,max = 25,message = "学生名字长度介于3到25之间")
    private String name;

    /**
     * 性别: 0女，1男，2其他
     */
    @NotNull(message = "性别不能为空")
    private String gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 入学年级,如1709级
     */
    @NotNull(message = "入学年级不能为空")
    @Length(min = 4, max = 4, message = "入学年级必须4位数字，例如1709")
    private String grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 身份证号码
     */
    @Length(min = 15,max = 18,message = "身份证必须是15位或者是18位数字")
    private String idNumber;
}