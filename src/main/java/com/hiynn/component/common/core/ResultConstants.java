package com.hiynn.component.common.core;

/**
 * 返回视图常量定义
 * <p>
 * 建议使用接口形式常量 减少冗余代码 简介 且不可更改
 * 返回信息长度为7位, 前三位为顶级类别,第四五位为产品序号从01开始,六七位01-99 与 大写A-Z的组合,00为公共的
 *
 * @author: Mr.Zhang
 * date: 2019/9/26 17:57
 */
public interface ResultConstants {

    /**
     * 错误码
     */
    interface Code {
        /**
         * 成功
         */
        String SUCCESS = "0000000";
        /**
         * 参数错误
         */
        String PARAM_ERROR = "0020000";
        /**
         * 权限验证错误
         */
        String AUTH_ERROR = "0030000";

        /**
         * 数据库错误
         */
        String DATA_ERROR = "0040000";
        /**
         * 数据重复
         */
        String DATA_DUPLICATE_ERROR = "0040001";
        /**
         * 系统级别错误,需要联系管理
         */
        String SYS_ERROR = "9990000";
        /**
         * 数组越界
         */
        String ARRAY_INDEX_OUT_ERROR = "9990001";
    }

    /**
     * 错误信息
     */
    interface Message {
        /**
         * 成功默认提示,可自定义
         */
        String SUCCESS_MSG = "success";
        /**
         * 参数错误默认提示,可自定义
         */
        String PARAM_ERROR_MSG = "参数错误";
        /**
         * 权限验证错误默认提示,可自定义
         */
        String AUTH_ERROR_MSG = "无权限";
        /**
         * 数据库错误默认提示，可自定义
         */
        String DATA_ERROR_MSG = "数据库错误";
        /**
         * 数据重复
         */
        String DATA_DUPLICATE_ERROR = "该数据已存在";
        /**
         * 系统错误默认提示,可自定义
         */
        String SYS_ERROR_MSG = "系统错误";
        /**
         * 数组越界
         */
        String ARRAY_INDEX_OUT_ERROR_MSG = "数组越界异常";
    }

}
