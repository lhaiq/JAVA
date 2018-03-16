package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.OpinionRecord;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionRecordRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("opinionrecord") OpinionRecord opinionrecord);

    int insertSelective(@Param("opinionrecord") OpinionRecord opinionrecord);

    OpinionRecord selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("opinionrecord") OpinionRecord opinionrecord);

    int updateByPrimaryKey(@Param("opinionrecord") OpinionRecord opinionrecord);

    int selectCount(@Param("opinionrecord") OpinionRecord opinionrecord);

    List<OpinionRecord> selectPage(@Param("opinionrecord") OpinionRecord opinionrecord, @Param("pageable") Pageable pageable);
}