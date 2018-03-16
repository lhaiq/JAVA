package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Hospital;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("hospital") Hospital hospital);

    int insertSelective(@Param("hospital") Hospital hospital);

    Hospital selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("hospital") Hospital hospital);

    int updateByPrimaryKey(@Param("hospital") Hospital hospital);

    int selectCount(@Param("hospital") Hospital hospital);

    List<Hospital> selectPage(@Param("hospital") Hospital hospital, @Param("pageable") Pageable pageable);
}