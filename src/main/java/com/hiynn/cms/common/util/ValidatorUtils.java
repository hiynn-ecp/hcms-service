package com.hiynn.cms.common.util;

import com.hiynn.cms.common.HCMSConstants;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 *  数据校验工具
 * @author 张朋
 * @date 2019/11/21 11:16
 */
public class ValidatorUtils {



    public static Boolean isBlank(String... param) {
        for (String s : param) {
            if (StringUtils.isBlank(s)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isNotNumberForString(String... param) {
        for (String s : param) {
            try {
                Double.parseDouble(s);
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isReg(String reg, String... param) {
        for (String s : param) {
            if (!Pattern.matches(reg, s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为 ofice 文档类文件
     *
     * @param
     * @return boolean
     * @author 张朋
     * @date 2019/11/18 15:49
     */
    public static boolean isDoc(String fileName) {
        return fileName.matches("^(.*(" + HCMSConstants.RegTemplate.OFFICE_EXT_REG + "))$");
    }

}
