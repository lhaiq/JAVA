package com.hengsu.bhyy.core.service.impl;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.Wallet;
import com.hengsu.bhyy.core.repository.WalletRepository;
import com.hengsu.bhyy.core.model.WalletModel;
import com.hengsu.bhyy.core.service.WalletService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private WalletRepository walletRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(WalletModel walletModel) {
		return walletRepo.insert(beanMapper.map(walletModel, Wallet.class));
	}

	@Transactional
	@Override
	public int createSelective(WalletModel walletModel) {
		return walletRepo.insertSelective(beanMapper.map(walletModel, Wallet.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return walletRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public WalletModel findByPrimaryKey(Long id) {
		Wallet wallet = walletRepo.selectByPrimaryKey(id);
		return beanMapper.map(wallet, WalletModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(WalletModel walletModel) {
		return walletRepo.selectCount(beanMapper.map(walletModel, Wallet.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<WalletModel> selectPage(WalletModel walletModel,Pageable pageable) {
		Wallet wallet = beanMapper.map(walletModel, Wallet.class);
		return beanMapper.mapAsList(walletRepo.selectPage(wallet,pageable),WalletModel.class);
	}

	@Override
	public List<WalletModel> selectByMonth(Long doctorId,String month, Pageable pageable) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		String endMonth =month;
		try {
			Date monthDate = DateUtils.addMonths(simpleDateFormat.parse(month),1);
			endMonth = simpleDateFormat.format(monthDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String sql = "SELECT id,doctor_id as doctorId,bill_id as billId,name,money,create_time as createTime\n" +
				"FROM wallet WHERE doctor_id = ? and create_time > ? and create_time <? ORDER BY create_time desc";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(WalletModel.class),doctorId,month,endMonth);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(WalletModel walletModel) {
		return walletRepo.updateByPrimaryKey(beanMapper.map(walletModel, Wallet.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(WalletModel walletModel) {
		return walletRepo.updateByPrimaryKeySelective(beanMapper.map(walletModel, Wallet.class));
	}

}
