package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.DoctorRecommend;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRecommendRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("doctorrecommend") DoctorRecommend doctorrecommend);

    int insertSelective(@Param("doctorrecommend") DoctorRecommend doctorrecommend);

    DoctorRecommend selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("doctorrecommend") DoctorRecommend doctorrecommend);

    int updateByPrimaryKey(@Param("doctorrecommend") DoctorRecommend doctorrecommend);

    int selectCount(@Param("doctorrecommend") DoctorRecommend doctorrecommend);

    List<DoctorRecommend> selectPage(@Param("doctorrecommend") DoctorRecommend doctorrecommend, @Param("pageable") Pageable pageable);
}