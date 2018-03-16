package com.hengsu.bhyy.core.repository;

import com.hengsu.bhyy.core.entity.CommentTagType;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentTagTypeRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("commenttagtype") CommentTagType commenttagtype);

    int insertSelective(@Param("commenttagtype") CommentTagType commenttagtype);

    CommentTagType selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("commenttagtype") CommentTagType commenttagtype);

    int updateByPrimaryKey(@Param("commenttagtype") CommentTagType commenttagtype);

    int selectCount(@Param("commenttagtype") CommentTagType commenttagtype);

    List<CommentTagType> selectPage(@Param("commenttagtype") CommentTagType commenttagtype, @Param("pageable") Pageable pageable);
}