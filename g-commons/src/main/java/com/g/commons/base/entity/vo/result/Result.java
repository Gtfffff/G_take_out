package com.g.commons.base.entity.vo.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Optional;

/**
 * @Author: Gtf
 * @Date: 2022/5/11-05-11-22:52
 * @Description: 统一响应格式
 * @Version: 1.0
 */
//spring需要根据get方法将类转为json对象
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result <T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private T data;

    private String msg;


    /**
     * 成功R
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(),data,ResultCode.SUCCESS.getMsg());
    }

    /**
     * 失败R
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed() {
        return result(ResultCode.FAILURE.getCode(), ResultCode.FAILURE.getMsg(), null);
    }

    public static <T> Result<T> failed(String msg) {
        return result(ResultCode.FAILURE.getCode(), msg, null);
    }
    public static <T> Result<T> failed(IResultCode resultCode) {
        return result(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> Result<T> failed(IResultCode resultCode, String msg) {
        return result(resultCode.getCode(), msg, null);
    }

    /**
     * 特殊R
     * @param resultCode
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Result<T> result(IResultCode resultCode, T data) {
        return result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    private static <T> Result<T> result(int code, String msg, T data) {
        return new Result<T>(code,data,msg);
    }

    public static <T> Result<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }

    public static boolean isSuccess(Result<?> result) {
        return (Boolean) Optional.ofNullable(result)
                .map((x) -> {
                    return ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.code, x.code);
                }).orElse(Boolean.FALSE);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
