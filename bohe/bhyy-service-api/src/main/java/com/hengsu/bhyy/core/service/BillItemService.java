
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.BillItemModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BillItemService{

public int create(BillItemModel billItemModel);

public int createSelective(BillItemModel billItemModel);

public BillItemModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(BillItemModel billItemModel);

public int updateByPrimaryKeySelective(BillItemModel billItemModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(BillItemModel billItemModel);

public List<BillItemModel> selectPage(BillItemModel billItemModel, Pageable pageable);

}