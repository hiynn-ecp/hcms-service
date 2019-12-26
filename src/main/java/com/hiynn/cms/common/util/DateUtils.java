package com.hiynn.cms.common.util;

import java.text.SimpleDateFormat;

/**
 * @author 张朋
 * @date 2019/10/30 11:31
 */
public final class DateUtils {

    public static final String DATE_FORMAT_YYYY_MM_DD__HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String getTime(String format) {
        return new SimpleDateFormat(format).format(System.currentTimeMillis());
    }

}
