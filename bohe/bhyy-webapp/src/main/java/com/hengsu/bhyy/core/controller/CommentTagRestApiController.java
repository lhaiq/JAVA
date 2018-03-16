package com.hengsu.bhyy.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import com.wlw.pylon.web.rest.annotation.RestApiController;

import com.hengsu.bhyy.core.service.CommentTagService;
import com.hengsu.bhyy.core.model.CommentTagModel;
import com.hengsu.bhyy.core.vo.CommentTagVO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class CommentTagRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CommentTagRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CommentTagService commentTagService;

	@GetMapping(value = "/core/commentTag/{id}")
	public ResponseEnvelope<CommentTagVO> getCommentTagById(@PathVariable Long id){
		CommentTagModel commentTagModel = commentTagService.findByPrimaryKey(id);
		CommentTagVO commentTagVO =beanMapper.map(commentTagModel, CommentTagVO.class);
		ResponseEnvelope<CommentTagVO> responseEnv = new ResponseEnvelope<>(commentTagVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/commentTag")
    public ResponseEnvelope<Page<CommentTagModel>> listCommentTag(CommentTagVO commentTagVO,Pageable pageable){

		CommentTagModel param = beanMapper.map(commentTagVO, CommentTagModel.class);
        List<CommentTagModel> commentTagModelModels = commentTagService.selectPage(param,pageable);
        long count=commentTagService.selectCount(param);
        Page<CommentTagModel> page = new PageImpl<>(commentTagModelModels,pageable,count);
        ResponseEnvelope<Page<CommentTagModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/commentTag")
	public ResponseEnvelope<Integer> createCommentTag(@RequestBody CommentTagVO commentTagVO){
		CommentTagModel commentTagModel = beanMapper.map(commentTagVO, CommentTagModel.class);
		Integer  result = commentTagService.createSelective(commentTagModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

	/**
	 * 搜索
	 * @param param
	 * @param pageable
	 * @return
	 */
	@PostMapping(value = "/core/commentTag/search")
	public ResponseEnvelope<Page<Map<String, Object>>> searchCommentTag(@RequestBody Map<String,String> param, Pageable pageable){
		Page<Map<String, Object>>  page = commentTagService.searchPage(param, pageable);
		ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

    @DeleteMapping(value = "/core/commentTag/{id}")
	public ResponseEnvelope<Integer> deleteCommentTagByPrimaryKey(@PathVariable Long id){
		Integer  result = commentTagService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/commentTag/{id}")
	public ResponseEnvelope<Integer> updateCommentTagByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody CommentTagVO commentTagVO){
		CommentTagModel commentTagModel = beanMapper.map(commentTagVO, CommentTagModel.class);
		commentTagModel.setId(id);
		Integer  result = commentTagService.updateByPrimaryKeySelective(commentTagModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
