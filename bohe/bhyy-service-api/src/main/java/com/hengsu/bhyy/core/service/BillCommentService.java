
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.BillCommentModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BillCommentService{

public int create(BillCommentModel billCommentModel);

public int createSelective(BillCommentModel billCommentModel);

public BillCommentModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(BillCommentModel billCommentModel);

public int updateByPrimaryKeySelective(BillCommentModel billCommentModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(BillCommentModel billCommentModel);

public List<BillCommentModel> selectPage(BillCommentModel billCommentModel, Pageable pageable);

}