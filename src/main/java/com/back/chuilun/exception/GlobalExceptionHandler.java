package com.back.chuilun.exception;

import com.back.chuilun.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @ControllerAdvice + @ExceptionHandler 实现全局的 Controller 层的异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Result handleException(Exception e){
        LOGGER.error(e.getMessage(), e);

        Result result = new Result<>();
        result.setMessage("操作失败！");
        return result;
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    Result handleBusinessException(BusinessException e){
        LOGGER.error(e.getMessage(), e);

        Result result = new Result<>();
        result.setMessage(e.getMessage());
        return result;
    }

    /**
     * 处理所有接口数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        LOGGER.error(e.getMessage(), e);

        Result result = new Result();
        result.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return result;
    }
}
