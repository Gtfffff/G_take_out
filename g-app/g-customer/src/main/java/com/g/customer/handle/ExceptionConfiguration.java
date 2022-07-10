package com.g.customer.handle;

import com.g.commons.base.entity.vo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class ExceptionConfiguration {
    @ExceptionHandler({ConstraintViolationException.class, BindException.class})
    public Result validateException(Exception ex, HttpServletRequest request) {
        log.error("Violation校验提示：" + ex.getMessage());
        String msg = null;
        if(ex instanceof ConstraintViolationException){
            ConstraintViolationException constraintViolationException = (ConstraintViolationException)ex;
            Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
            ConstraintViolation<?> next = violations.iterator().next();
            msg = next.getMessage();
        }else if(ex instanceof BindException){
            BindException bindException = (BindException)ex;
            msg = bindException.getBindingResult().getFieldError().getDefaultMessage();
        }
        return Result.failed(msg);
    }
}