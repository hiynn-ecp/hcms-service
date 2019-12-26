package com.hiynn.cms.common.exception;

import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.component.common.core.Result;
import com.hiynn.component.common.core.ResultConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

/**
 * 各种异常
 *
 * @author 张朋
 * @date 2019/10/24 17:12
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        assert request != null;
        return request;
    }

    private void printLog() {
        HttpServletRequest request = getRequest();
        log.info("--------- 请求:[{}][{}]---------", request.getMethod(), request.getRequestURI());
    }

    /**
     * 接口请求如不支持GET POST的时候 提示给前端
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public Result authorizationException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法错误:{}", e.getMessage());
        printLog();
        return HCMSConstants.ResultTemplate.METHOD_NOT_SUPPORTE;
    }

    /**
     * shiro 权限异常
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = {AuthorizationException.class})
    public Result authorizationException(AuthorizationException e) {
        log.error("操作未授权:{}", e.getMessage());
        return HCMSConstants.ResultTemplate.INVALID_AUTH;

    }

    /**
     * shiro 用户锁定异常
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = LockedAccountException.class)
    public Result lockedAccountException(LockedAccountException e) {
        log.error("用户锁定:{}", e.getMessage());
        return HCMSConstants.ResultTemplate.LOCKED_ACCOUNT;

    }

    /**
     * shiro 用户不存在
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = UnknownAccountException.class)
    public Result unknownAccountException(UnknownAccountException e) {
        log.error("用户不存在:{}", e.getMessage());
        return HCMSConstants.ResultTemplate.UNKNOWN_ACCOUNT;

    }

    /**
     * shiro 密码错误
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public Result IncorrectCredentialsException(IncorrectCredentialsException e) {
        log.error("密码错误:{}", e.getMessage());
        return HCMSConstants.ResultTemplate.INVALID_PASSWORD;
    }

    /**
     * spring mvc 方法参数错误
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("参数异常:{}", e.getMessage());
        return HCMSConstants.ResultTemplate.PATAM_ERROR;
    }

    /**
     * spring mvc 方法参数错误
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("参数异常:{}", e.getMessage());
        return HCMSConstants.ResultTemplate.PATAM_ERROR;
    }


    /**
     * spring接收请求参数时反射错误 ，类型不匹配
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数结构异常:{}", e.getMessage());
        return HCMSConstants.ResultTemplate.PATAM_ERROR;
    }

    /**
     * 入库时 参数包含了不匹配的编码符号
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = {UncategorizedSQLException.class})
    public Result handlerUncategorizedSQLException(UncategorizedSQLException e) {
        log.error("不匹配的编码符号:{}", e.getMessage());
        int errorCode = e.getSQLException().getErrorCode();
        switch (errorCode) {
            case 1366:
            case 1267:
                return Result.errorParam().setReturnMessage("不匹配的编码符号");
            default:
                return Result.errorSystem();
        }
    }

    /**
     * 参数校验使用 @Validated 时返回的错误
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = {BindException.class})
    public Result handlerBindException(BindException e) {
        log.error("DTO参数校验异常:{}", e.getMessage());
        FieldError fieldError = e.getFieldError();
        String defaultMessage = null;
        if (fieldError != null) {
            defaultMessage = fieldError.getDefaultMessage();
        } else {
            defaultMessage = e.getMessage();
        }
        if (defaultMessage == null) {
            defaultMessage = fieldError.getField() + ":" + "错误信息没有设置,请联系后台管理";
        }
        return Result.errorParam().setReturnMessage(defaultMessage);
    }

    /**
     * 参数校验使用2  当参数使用 requestBody 的时候 @Validated 触发的 为MethodArgumentNotValidException
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("JSON参数校验异常:{}", e.getMessage());
        FieldError fieldError = e.getBindingResult().getFieldError();
        String defaultMessage = null;
        if (fieldError != null) {
            defaultMessage = fieldError.getDefaultMessage();
        } else {
            defaultMessage = e.getMessage();
        }
        if (defaultMessage == null) {
            defaultMessage = fieldError.getField() + ":" + "错误信息没有设置,请联系后台管理";
        }
        return Result.errorParam().setReturnMessage(defaultMessage);
    }

    /**
     * 参数校验使用3  当对象上使用 @Validated 对象的方法行参进行校验 触发的 ConstraintViolationException
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Result constraintViolationException(ConstraintViolationException e) {
        log.error("行参校验异常:{}", e.getMessage());
        String message = e.getMessage();
        String[] split = message.split(":");
        if (split.length == 1) {

            return Result.errorParam().setReturnMessage("错误信息没有设置,请联系后台管理");
        }
        return Result.errorParam().setReturnMessage(split[1]);
    }


    /**
     * 数组越界
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    public Result arrayIndexOutOfBoundsExceptionHandler(ArrayIndexOutOfBoundsException e) {
        log.error("数组越界:{}", e.getMessage());
        return Result.build()
                .setReturnCode(ResultConstants.Code.ARRAY_INDEX_OUT_ERROR)
                .setReturnMessage(ResultConstants.Message.ARRAY_INDEX_OUT_ERROR_MSG);

    }

    /**
     * 数据库异常
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = SQLException.class)
    public Result SQLExceptionHandler(SQLException e) {
        log.error("数据库异常:{}", e.getMessage());
        return Result.build()
                .setReturnCode(ResultConstants.Code.DATA_ERROR)
                .setReturnMessage(ResultConstants.Message.DATA_ERROR_MSG);

    }

    /**
     * 触发数据库唯一索引
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    public Result duplicateKeyExceptionHandler(DuplicateKeyException e) {
        log.error("唯一数据重复:{}", e.getMessage());
        return Result.build()
                .setReturnCode(ResultConstants.Code.DATA_DUPLICATE_ERROR)
                .setReturnMessage(ResultConstants.Message.DATA_DUPLICATE_ERROR);
    }

    /**
     * 自定义异常返回
     * <p>
     * 可返回Result对象
     * <p>
     * 利用异常进行result 返回 减少 一层一层的去判断
     * <p>
     * 直接在错误位置中断处理 是最保守、 最保险、 最安全、 最效率、 最简洁、的处理方式
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = ResultException.class)
    public Result resultException(ResultException e) {
        log.error("ResultException:{}", e.getResult().getReturnMessage());
        return e.getResult();
    }

    /**
     * 其他运行异常
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result runtimeException(RuntimeException e) {
        log.error("{}", e);
        return Result.build()
                .setReturnCode(ResultConstants.Code.SYS_ERROR)
                .setReturnMessage(e.getMessage());
    }

    /**
     * 其他未知异常
     *
     * @author 张朋
     * @date 2019/11/21 14:56
     */
    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e) {
        log.error("{}", e);
        return Result.build()
                .setReturnCode(ResultConstants.Code.SYS_ERROR)
                .setReturnMessage(e.getMessage());
    }


}
