package com.taoyyz.user.web.controller;

import com.taoyyz.user.common.JsonResponse;
import com.taoyyz.user.model.domain.Grade;
import com.taoyyz.user.service.GradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 前端控制器
 *
 * @author taoyyz
 * @version v1.0
 * @since 2021-10-20
 */
@Controller
@RequestMapping("/grade")
public class GradeController {

    private final Logger logger = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Grade grade = gradeService.getById(id);
        return JsonResponse.success(grade);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        gradeService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateGrade(@PathVariable("id") Long id, Grade grade) throws Exception {
        grade.setId(id);
        gradeService.updateById(grade);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建Grade
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Grade grade) throws Exception {
        gradeService.save(grade);
        return JsonResponse.success(null);
    }
}

