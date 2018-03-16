package com.hengsu.bhyy.core;

import com.wlw.pylon.core.ErrorCode;

/**
 * Created by haiquanli on 16/7/28.
 */
public class HRErrorCode extends ErrorCode {

    public static final ErrorCode UN_LOGIN = ErrorCode("10000", "未登录或session过期");
    public static final ErrorCode CODE_NOT_EXISTED = ErrorCode("10001", "验证码不存在");
    public static final ErrorCode CODE_ERROR = ErrorCode("10002", "验证码错误");
    public static final ErrorCode DOCTOR_NOT_EXISTED = ErrorCode("10003", "医生账号不存在");

    public static final ErrorCode CONNOT_DELETE_CONFIG = ErrorCode("10003", "已有预约产生,不能删除出诊安排");

    public static final ErrorCode IMAGE_NOT_EXISTED = ErrorCode("50004", "图片不存在");
    public static final ErrorCode IMAGE_UPLOAD_ERROR = ErrorCode("50005", "图片上传失败");

    public static final ErrorCode ACCOUNT_ERROR = ErrorCode("10002", "帐号不存在或密码错误");
    public static final ErrorCode CONFIG_NOT_EXISTED_ERROR = ErrorCode("10002", "出诊安排不存在");


    protected HRErrorCode(String code, String errorMsg) {
        super(code, errorMsg);
    }
}
