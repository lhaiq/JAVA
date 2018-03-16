package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.Wallet;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("wallet") Wallet wallet);

    int insertSelective(@Param("wallet") Wallet wallet);

    Wallet selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("wallet") Wallet wallet);

    int updateByPrimaryKey(@Param("wallet") Wallet wallet);

    int selectCount(@Param("wallet") Wallet wallet);

    List<Wallet> selectPage(@Param("wallet") Wallet wallet, @Param("pageable") Pageable pageable);
}