package com.hiynn.component.common.core;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.io.Serializable;

/**
 * 本类包含了一些默认的 返回信息设置方法 可根据业务协议自定义返回码与提示信息
 *
 * @author: zhangpeng
 * date: 2019/4/12 10:02
 */
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "返回结果模型")
public class Result implements Serializable {

    private static final long serialVersionUID = -3106881043934299865L;

    @ApiModelProperty(value = "数据")
    private Object data;
    @ApiModelProperty(value = "返回码")
    private String returnCode;
    @ApiModelProperty(value = "提示信息")
    private String returnMessage;

    /**
     * 禁止手动创建 强制要求自动初始化
     */
    private Result() {
    }

    public static Result build() {
        return new Result();
    }

    /**
     * 简单返回成功
     *
     * @return
     */
    public static Result success() {
        return build()
                .setReturnCode(ResultConstants.Code.SUCCESS);
    }

    /**
     * 返回成功信息
     *
     * @param data 返回信息
     * @return
     */
    public static Result success(Object data) {
        return build()
                .setReturnCode(ResultConstants.Code.SUCCESS)
                .setData(data);
    }

    /**
     * 自定义失败
     *
     * @param returnCode    错误码
     * @param returnMessage 提示信息
     * @return
     */
    public static Result error(String returnCode, String returnMessage) {
        return build()
                .setReturnCode(returnCode)
                .setReturnMessage(returnMessage);
    }

    /**
     * 参数错误
     *
     * @return
     */
    public static Result errorParam() {
        return build()
                .setReturnCode(ResultConstants.Code.PARAM_ERROR)
                .setReturnMessage(ResultConstants.Message.PARAM_ERROR_MSG);
    }

    /**
     * 权限验证错误
     *
     * @return
     */
    public static Result errorAuth() {
        return build()
                .setReturnCode(ResultConstants.Code.AUTH_ERROR)
                .setReturnMessage(ResultConstants.Message.AUTH_ERROR_MSG);
    }

    public static Result errorSystem() {
        return build()
                .setReturnCode(ResultConstants.Code.SYS_ERROR)
                .setReturnMessage(ResultConstants.Message.SYS_ERROR_MSG);
    }

    /**
     * Excel导入失败（批量导入时，会返回校验不通过的列表数据） add by zhanjian 2019年11月6日15:53:07
     * @param returnCode 错误码
     * @param data 返回信息
     * @return
     */
    public static Result errorImport(String returnCode, Object data) {
        return build()
                .setReturnCode(returnCode)
                .setData(data);
    }

    public String getReturnCode() {
        return returnCode;
    }

    public Result setReturnCode(String returnCode) {
        this.returnCode = returnCode;
        return this;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public Result setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public <T> T dataCast(Class<T> clazz) {
        return clazz.cast(this.data);
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }


    public String toJSONString() {
        return JSON.toJSONString(this);
    }


}
