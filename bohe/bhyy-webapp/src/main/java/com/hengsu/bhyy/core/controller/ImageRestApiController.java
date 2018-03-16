package com.hengsu.bhyy.core.controller;

import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.RandomUtil;
import com.hengsu.bhyy.core.annotation.IgnoreAuth;
import com.wlw.pylon.core.ErrorCode;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

import com.hengsu.bhyy.core.service.ImageService;
import com.hengsu.bhyy.core.model.ImageModel;
import com.hengsu.bhyy.core.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bhyy")
public class ImageRestApiController {

    private final Logger logger = LoggerFactory.getLogger(ImageRestApiController.class);

    @Autowired
    private ImageService imageService;

    @Value("${photo.base.url}")
    private String baseDirectory;

    @IgnoreAuth
    @GetMapping(value = "/core/image/{id}")
    public void getPhotoById(@PathVariable Long id,
                             @RequestParam(value = "width", required = false) Integer width,
                             @RequestParam(value = "height", required = false) Integer height,
                             HttpServletResponse response) {

        ImageModel imageModel = imageService.findByPrimaryKey(id);
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            inputStream = FileUtils.openInputStream(new File(baseDirectory + "/" + imageModel.getPath()));
            if (null == width && null == height) {
                IOUtils.copy(inputStream, outputStream);
            } else {
                Thumbnails.of(inputStream)
                        .size(width, height)
                        .toOutputStream(outputStream);
            }

        } catch (IOException e) {
            logger.error("expected error ", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

    }

    @IgnoreAuth
    @PostMapping(value = "/core/image")
    public ResponseEnvelope<Long> createImage(@RequestParam MultipartFile file,
                                                 @RequestParam String type) {

        String filename = RandomUtil.createRandom(true, 12);
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String path = type + "/" + filename + "." + extension;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = FileUtils.openOutputStream(new File(baseDirectory + "/" + path));
            IOUtils.copy(inputStream, outputStream);

        } catch (IOException e) {
            logger.error("expected error ", e);
            ErrorCode.throwBusinessException(HRErrorCode.IMAGE_UPLOAD_ERROR);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

        ImageModel imageModel = new ImageModel();
        imageModel.setType(type);
        imageModel.setFileName(file.getName());
        imageModel.setExtension(extension);
        imageModel.setPath(path);
        imageService.createSelective(imageModel);
        ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(imageModel.getId(), true);
        return responseEnv;
    }

//    @DeleteMapping(value = "/core/image/{id}")
//	public ResponseEnvelope<Integer> deleteImageByPrimaryKey(@PathVariable Long id){
//		Integer  result = imageService.deleteByPrimaryKey(id);
//		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
//        return responseEnv;
//	}


}
