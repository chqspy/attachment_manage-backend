package com.define.common.exception.business;

import com.define.common.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class BusinessExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 自定义业务异常
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public R handleBusinessException(BusinessException e) {
        logger.error(e.getErrorMsg(), e);
        String msg = "";
        Object obj = null;
        int code = 500;
        if (StringUtils.isNotEmpty(e.getErrorMsg())) {
            msg = e.getErrorMsg();
        } else {
            msg = e.getMessage();
        }
        if (e.getErrorObj() != null) {
            obj = e.getErrorObj();
        } else {
            obj = e.getCause();
        }
        code = e.getErrorCode();
        return R.error(code, msg);
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error("服务器错误，请联系管理员");
    }

}
