package com.hiynn.cms.common;

import com.hiynn.component.common.core.Result;
import com.hiynn.component.common.core.ResultConstants;

/**
 * 各种常量定义
 *
 * @author 张朋
 * @date 2019/10/24 17:11
 */
public interface HCMSConstants {
    /**
     * 常量Result对象
     */
    interface ResultTemplate {
        /**
         * 参数异常
         */
        Result PATAM_ERROR = Result.error(ResultConstants.Code.PARAM_ERROR, ResultConstants.Message.PARAM_ERROR_MSG);
        /**
         * 用户被锁定
         */
        Result LOCKED_ACCOUNT = Result.error("0038801", "用户被锁定");
        /**
         * 用户不存在
         */
        Result UNKNOWN_ACCOUNT = Result.error("0038802", "用户不存在");
        /**
         * 密码错误
         */
        Result INVALID_PASSWORD = Result.error("0038803", "密码错误");
        /**
         * 操作未授权
         */
        Result INVALID_AUTH = Result.error("0038804", "操作未授权");
        /**
         * 未登录
         */
        Result NOT_LOGIN = Result.error("0038805", "未登录");
        /**
         * 操作失败,不存在的数据或提供的关键参数无效
         */
        Result EXECUT_ERROR = Result.error("0038806", "操作失败,不存在的数据或提供的关键参数无效");
        /**
         * 账号已存在
         */
        Result ACCOUNT_EXISTS = Result.error("0048801", "账号已存在");
        /**
         * 请求方法错误
         */
        Result METHOD_NOT_SUPPORTE = Result.error("9998800", "HTTP请求方法错误");
    }


    /**
     * HCMS 系统常量
     */
    interface System {
        /**
         * 系统名称
         */
        String PROJECT_NAME = "HCMS";
        String PROJECT_NAME_CN = "云豹系统-（海云内容管理系统）";
    }


    /**
     * 正则模板
     *
     * @author 张朋
     * @date 2019/11/25 11:04
     */
    interface RegTemplate {

        /**
         * 时间正则
         */
        String Reg_HH_mm_ss = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        String Reg_HHmmss = "^([0-1]?[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$";
        String Reg_yyyy_MM_dd__HH_mm_ss = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        String REG_yyyyMMddHHmmss = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))([0-1]?[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$";
        String REG_yyyy_MM_dd = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$";
        String REG_yyyyMMdd = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))$";

        /**
         * uuid 格式校验
         */
        String REG_UUID = "^([0-9a-z]{1,32})$";

        String OFFICE_EXT_REG = ".xls|.xlsx|.doc|.docx|.ppt|.pptx";

    }

    /**
     * 枚举类型常量
     *
     * @author 张朋
     * @date 2019/11/25 11:04
     */
    interface EnumType {
        /**
         * 菜单类型
         */
        String MENU_TYPE = "menu_type";

        /**
         * 文章资料类型
         */
        String ARTICLE_DATA_TYPE = "sys_article_data_type";
    }
}
