package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.model.CaseTemplateModel;
import com.hengsu.bhyy.core.service.CaseTemplateService;
import com.hengsu.bhyy.core.vo.CaseTemplateVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class CaseTemplateRestApiController {

    private final Logger logger = LoggerFactory.getLogger(CaseTemplateRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private CaseTemplateService caseTemplateService;


    @GetMapping(value = "/core/{categoryId}/caseTemplate")
    public ResponseEnvelope<CaseTemplateModel> getCaseTemplateByCategoryId(@PathVariable Long categoryId,
                                                                           @RequestParam Integer type) {
        CaseTemplateModel param = new CaseTemplateModel();
        param.setCategoryId(categoryId);
        param.setType(type);
        List<CaseTemplateModel> caseTemplateModels = caseTemplateService.selectPage(param, new PageRequest(0, 1));
        CaseTemplateModel caseTemplateModel = null;
        if (CollectionUtils.isNotEmpty(caseTemplateModels)) {
            caseTemplateModel = caseTemplateModels.get(0);
        }
        ResponseEnvelope<CaseTemplateModel> responseEnv = new ResponseEnvelope<>(caseTemplateModel, true);
        return responseEnv;
    }


    @PostMapping(value = "/core/caseTemplate")
    public ResponseEnvelope<Integer> createCaseTemplate(@RequestBody CaseTemplateVO caseTemplateVO) {

        CaseTemplateModel caseTemplateModel = beanMapper.map(caseTemplateVO, CaseTemplateModel.class);
        CaseTemplateModel param = new CaseTemplateModel();
        param.setCategoryId(caseTemplateVO.getCategoryId());
        param.setType(caseTemplateVO.getType());
        List<CaseTemplateModel> caseTemplateModels = caseTemplateService.selectPage(param, new PageRequest(0, 1));
        if (CollectionUtils.isEmpty(caseTemplateModels)) {
            caseTemplateService.createSelective(caseTemplateModel);
        } else {
            caseTemplateModel.setId(caseTemplateModels.get(0).getId());
            caseTemplateService.updateByPrimaryKeySelective(caseTemplateModel);
        }

        ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(1, true);
        return responseEnv;
    }

}
