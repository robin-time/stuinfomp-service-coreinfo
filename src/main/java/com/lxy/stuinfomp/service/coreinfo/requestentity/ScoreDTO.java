package com.lxy.stuinfomp.service.coreinfo.requestentity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreDTO {
    /**
     * 学生表学生id
     */
    @NotNull(message = "学生不能为空")
    private Integer sid;

    /**
     * 课程表课程id
     */
    @NotNull(message = "课程不能为空")
    private Integer cid;

    /**
     *  成绩得分
     */
    private Integer score;
}
