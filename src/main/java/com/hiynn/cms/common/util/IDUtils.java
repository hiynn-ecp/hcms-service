package com.hiynn.cms.common.util;

import com.hiynn.cms.common.HCMSConstants;

import java.util.StringTokenizer;
import java.util.UUID;

/**
 * ID工具类
 *
 * @author 张朋
 * @date 2019/10/30 11:31
 */
public class IDUtils {

    /**
     * javaUUID
     *
     * @return java.lang.String
     * @author 张朋
     * @date 2019/10/30 15:57
     */
    public static String getJavaUUID() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(uuid.toString(), "-");
        return sb.append(st.nextToken()).
                append(st.nextToken()).
                append(st.nextToken()).
                append(st.nextToken()).
                append(st.nextToken()).
                toString();
    }

}
