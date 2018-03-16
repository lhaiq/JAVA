
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.WalletModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface WalletService{

public int create(WalletModel walletModel);

public int createSelective(WalletModel walletModel);

public WalletModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(WalletModel walletModel);

public int updateByPrimaryKeySelective(WalletModel walletModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(WalletModel walletModel);

public List<WalletModel> selectPage(WalletModel walletModel, Pageable pageable);

}