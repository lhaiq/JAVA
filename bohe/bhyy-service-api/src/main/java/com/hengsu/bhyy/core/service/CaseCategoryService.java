
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.CaseCategoryModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CaseCategoryService{

public int create(CaseCategoryModel caseCategoryModel);

public int createSelective(CaseCategoryModel caseCategoryModel);

public CaseCategoryModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(CaseCategoryModel caseCategoryModel);

public int updateByPrimaryKeySelective(CaseCategoryModel caseCategoryModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(CaseCategoryModel caseCategoryModel);

public List<CaseCategoryModel> selectPage(CaseCategoryModel caseCategoryModel, Pageable pageable);

}