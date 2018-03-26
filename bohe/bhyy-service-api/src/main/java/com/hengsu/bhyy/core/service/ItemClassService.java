
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.ItemClassModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ItemClassService {

    int create(ItemClassModel itemClassModel);

    int createSelective(ItemClassModel itemClassModel);

    ItemClassModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(ItemClassModel itemClassModel);

    int updateByPrimaryKeySelective(ItemClassModel itemClassModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(ItemClassModel itemClassModel);

    List<ItemClassModel> selectPage(ItemClassModel itemClassModel, Pageable pageable);

    Page<Map<String,Object>> selectByType(int type, String name,Pageable pageable);

}