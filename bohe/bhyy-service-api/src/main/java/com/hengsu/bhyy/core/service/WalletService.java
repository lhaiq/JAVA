
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.WalletModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface WalletService{

 int create(WalletModel walletModel);

 int createSelective(WalletModel walletModel);

 WalletModel findByPrimaryKey(Long id);

 int updateByPrimaryKey(WalletModel walletModel);

 int updateByPrimaryKeySelective(WalletModel walletModel);

 int deleteByPrimaryKey(Long id);

 long selectCount(WalletModel walletModel);

 List<WalletModel> selectPage(WalletModel walletModel, Pageable pageable);

 List<WalletModel> selectByMonth(Long doctorId,String month, Pageable pageable);

}