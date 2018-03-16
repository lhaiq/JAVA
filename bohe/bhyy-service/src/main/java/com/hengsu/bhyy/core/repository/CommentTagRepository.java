package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.CommentTag;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentTagRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("commenttag") CommentTag commenttag);

    int insertSelective(@Param("commenttag") CommentTag commenttag);

    CommentTag selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("commenttag") CommentTag commenttag);

    int updateByPrimaryKey(@Param("commenttag") CommentTag commenttag);

    int selectCount(@Param("commenttag") CommentTag commenttag);

    List<CommentTag> selectPage(@Param("commenttag") CommentTag commenttag, @Param("pageable") Pageable pageable);
}