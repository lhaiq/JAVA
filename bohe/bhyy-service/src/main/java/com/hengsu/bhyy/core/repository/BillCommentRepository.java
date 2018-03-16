package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.BillComment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillCommentRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("billcomment") BillComment billcomment);

    int insertSelective(@Param("billcomment") BillComment billcomment);

    BillComment selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("billcomment") BillComment billcomment);

    int updateByPrimaryKey(@Param("billcomment") BillComment billcomment);

    int selectCount(@Param("billcomment") BillComment billcomment);

    List<BillComment> selectPage(@Param("billcomment") BillComment billcomment, @Param("pageable") Pageable pageable);
}