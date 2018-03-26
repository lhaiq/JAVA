package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.NotifyMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyMessageRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("notifymessage") NotifyMessage notifymessage);

    int insertSelective(@Param("notifymessage") NotifyMessage notifymessage);

    NotifyMessage selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("notifymessage") NotifyMessage notifymessage);

    int updateByPrimaryKey(@Param("notifymessage") NotifyMessage notifymessage);

    int selectCount(@Param("notifymessage") NotifyMessage notifymessage);

    List<NotifyMessage> selectPage(@Param("notifymessage") NotifyMessage notifymessage, @Param("pageable") Pageable pageable);
}