package com.hiynn.component.common.core.exception;

import com.hiynn.component.common.core.ResultConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 * date 2019/9/19 16:25
 * @author xuxitan
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class CoreException extends RuntimeException {

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 一般异常
     *
     * @param message 异常信息
     */
    public CoreException(String message) {
        this.code = ResultConstants.Code.SYS_ERROR;
        this.message = message;
    }

    /**
     * 自定义异常码
     *
     * @param code 异常编码
     * @param message  异常信息
     */
    public CoreException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
