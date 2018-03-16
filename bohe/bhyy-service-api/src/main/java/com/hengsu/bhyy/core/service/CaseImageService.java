
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CaseImageModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CaseImageService{

 int create(CaseImageModel caseImageModel);

 int createSelective(CaseImageModel caseImageModel);

 CaseImageModel findByPrimaryKey(Long id);

 int updateByPrimaryKey(CaseImageModel caseImageModel);

 int updateByPrimaryKeySelective(CaseImageModel caseImageModel);

 int deleteByPrimaryKey(Long id);

 long selectCount(CaseImageModel caseImageModel);

 List<CaseImageModel> selectPage(CaseImageModel caseImageModel, Pageable pageable);

}