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

import com.hengsu.bhyy.core.service.CommentTagTypeService;
import com.hengsu.bhyy.core.model.CommentTagTypeModel;
import com.hengsu.bhyy.core.vo.CommentTagTypeVO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bhyy")
public class CommentTagTypeRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CommentTagTypeRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CommentTagTypeService commentTagTypeService;

	@GetMapping(value = "/core/commentTagType/{id}")
	public ResponseEnvelope<CommentTagTypeVO> getCommentTagTypeById(@PathVariable Long id){
		CommentTagTypeModel commentTagTypeModel = commentTagTypeService.findByPrimaryKey(id);
		CommentTagTypeVO commentTagTypeVO =beanMapper.map(commentTagTypeModel, CommentTagTypeVO.class);
		ResponseEnvelope<CommentTagTypeVO> responseEnv = new ResponseEnvelope<>(commentTagTypeVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/commentTagType")
    public ResponseEnvelope<Page<CommentTagTypeModel>> listCommentTagType(CommentTagTypeVO commentTagTypeVO,Pageable pageable){

		CommentTagTypeModel param = beanMapper.map(commentTagTypeVO, CommentTagTypeModel.class);
        List<CommentTagTypeModel> commentTagTypeModelModels = commentTagTypeService.selectPage(param,pageable);
        long count=commentTagTypeService.selectCount(param);
        Page<CommentTagTypeModel> page = new PageImpl<>(commentTagTypeModelModels,pageable,count);
        ResponseEnvelope<Page<CommentTagTypeModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/commentTagType")
	public ResponseEnvelope<Integer> createCommentTagType(@RequestBody CommentTagTypeVO commentTagTypeVO){
		CommentTagTypeModel commentTagTypeModel = beanMapper.map(commentTagTypeVO, CommentTagTypeModel.class);
		Integer  result = commentTagTypeService.createSelective(commentTagTypeModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

	/**
	 * 搜索
	 * @param param
	 * @param pageable
	 * @return
	 */
	@PostMapping(value = "/core/commentTagType/search")
	public ResponseEnvelope<Page<Map<String, Object>>> searchCommentTagType(@RequestBody Map<String,String> param, Pageable pageable){
		Page<Map<String, Object>>  page = commentTagTypeService.searchPage(param, pageable);
		ResponseEnvelope<Page<Map<String, Object>>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

    @DeleteMapping(value = "/core/commentTagType/{id}")
	public ResponseEnvelope<Integer> deleteCommentTagTypeByPrimaryKey(@PathVariable Long id){
		Integer  result = commentTagTypeService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/commentTagType/{id}")
	public ResponseEnvelope<Integer> updateCommentTagTypeByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody CommentTagTypeVO commentTagTypeVO){
		CommentTagTypeModel commentTagTypeModel = beanMapper.map(commentTagTypeVO, CommentTagTypeModel.class);
		commentTagTypeModel.setId(id);
		Integer  result = commentTagTypeService.updateByPrimaryKeySelective(commentTagTypeModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
