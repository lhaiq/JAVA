
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CaseTemplateModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CaseTemplateService{

public int create(CaseTemplateModel caseTemplateModel);

public int createSelective(CaseTemplateModel caseTemplateModel);

public CaseTemplateModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(CaseTemplateModel caseTemplateModel);

public int updateByPrimaryKeySelective(CaseTemplateModel caseTemplateModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(CaseTemplateModel caseTemplateModel);

public List<CaseTemplateModel> selectPage(CaseTemplateModel caseTemplateModel, Pageable pageable);

}