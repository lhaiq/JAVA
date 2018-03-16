
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.BannerModel;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BannerService {

    int create(BannerModel bannerModel);

    int createSelective(BannerModel bannerModel);

    BannerModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(BannerModel bannerModel);

    int updateByPrimaryKeySelective(BannerModel bannerModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(BannerModel bannerModel);

    List<BannerModel> selectPage(BannerModel bannerModel, Pageable pageable);

    Page<Map<String, Object>> searchPage(Map<String, String> param, Pageable pageable);

}