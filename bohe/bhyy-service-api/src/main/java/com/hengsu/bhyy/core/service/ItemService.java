
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.ItemModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ItemService{

public int create(ItemModel itemModel);

public int createSelective(ItemModel itemModel);

public ItemModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(ItemModel itemModel);

public int updateByPrimaryKeySelective(ItemModel itemModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(ItemModel itemModel);

public List<ItemModel> selectPage(ItemModel itemModel, Pageable pageable);

}