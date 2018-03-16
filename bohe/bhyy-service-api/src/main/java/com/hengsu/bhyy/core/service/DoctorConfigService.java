
package com.hengsu.bhyy.core.service;

import com.hengsu.bhyy.core.model.DoctorConfigModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DoctorConfigService {

    int create(DoctorConfigModel doctorConfigModel);

    int createSelective(DoctorConfigModel doctorConfigModel);

    DoctorConfigModel findByPrimaryKey(Long id);

    int updateByPrimaryKey(DoctorConfigModel doctorConfigModel);

    int updateByPrimaryKeySelective(DoctorConfigModel doctorConfigModel);

    int deleteByPrimaryKey(Long id);

    long selectCount(DoctorConfigModel doctorConfigModel);

    List<DoctorConfigModel> selectPage(DoctorConfigModel doctorConfigModel, Pageable pageable);

    List<Map<String, Object>> selectConfigByName(String name);


}