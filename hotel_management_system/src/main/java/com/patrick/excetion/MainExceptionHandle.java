package com.patrick.excetion;

import com.patrick.bean.RespBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class MainExceptionHandle {
    @ExceptionHandler(MyException.class)
    public RespBean MyExceptionHandler(MyException e){
        e.printStackTrace();
        return RespBean.error(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public RespBean runtimeexceptionHandler(RuntimeException e) {
        e.printStackTrace();
        return RespBean.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandle(Exception e){
        e.printStackTrace();
        return RespBean.error("未知异常,请联系Patrick工作人员");
    }
    @ExceptionHandler({BindException.class})
    public RespBean handleMethodArgumentNotValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sbf = new StringBuilder("添加失败 ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {

            sbf.append(fieldError.getDefaultMessage()).append(", ");
break;
        }
        String msg = sbf.toString();
        int i = msg.lastIndexOf(", ");
        msg = msg.substring(0, i);
        return RespBean.error(msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public RespBean handleConstraintViolationException(ConstraintViolationException e) {
        return RespBean.error(e.getMessage());
    }
}
