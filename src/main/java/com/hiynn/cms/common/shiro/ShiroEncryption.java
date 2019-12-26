package com.hiynn.cms.common.shiro;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * shiro的加密工具类
 *
 * @author 张朋
 * @date $date$ $time$
 */
public class ShiroEncryption {

    /**
     * 对用户的密码进行MD5加密
     * <p>
     * 验证用户时 传入用户输入的密码 和 查询到的salt 进行 shiro 验证使用
     *
     * @param password
     * @param salt
     * @return java.lang.String 加密后的密码
     * @author 张朋
     * @date 2019/10/28 17:53
     */
    public static String shiroEncryption(String password, String salt) {
        // 返回加密后的密码
        return new SimpleHash("md5", password, salt, 2).toString();
    }

    /**
     * shiro 自带的工具类生成salt
     * <p>
     * 注册用户时 使用
     *
     * @return java.lang.String
     * @author 张朋
     * @date 2019/10/28 17:50
     */
    public static String getSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toString();
    }


}
