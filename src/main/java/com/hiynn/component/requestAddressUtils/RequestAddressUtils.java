package com.hiynn.component.requestAddressUtils;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 请求地址获取
 *
 * @author: Mr.Zhang
 * @email zhangpeng@hiynn.com
 * @date: 2019/6/18 10:33
 */
public class RequestAddressUtils {


    /**
     * ip 库 搜索引擎
     */
    private static DbSearcher searcher = null;
    /**
     * ip 库路径
     */
    private static InputStream dbFile = RequestAddressUtils.class.getResourceAsStream("/ip2region/ip2region.db");
    /**
     * DbSearcher 搜因引擎使用的锁
     */
    private final static Lock lock = new ReentrantLock();


    /**
     * 归属地获取
     *
     * @param ip
     * @return
     */
    public static String getAttribution(String ip) throws DbMakerConfigException, IOException {

        DataBlock block;
        DbConfig config = new DbConfig();
        String region;

        try {
            lock.lock();
            if (searcher == null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = 1024;
                byte tmp[] = new byte[len];
                int i;
                while ((i = dbFile.read(tmp, 0, len)) > 0) {
                    baos.write(tmp, 0, i);
                }
                searcher = new DbSearcher(config, baos.toByteArray());
            }
            block = searcher.memorySearch(ip);
        } finally {
            lock.unlock();
        }
        region = block.getRegion();

        String[] split = region.split("\\|");

        if (!split[2].equals("0")) {
            return split[2];
        }
        if (!split[3].equals("0")) {
            return split[3];
        }
        if (!split[1].equals("0")) {
            return split[1];
        }
        if (!split[0].equals("0")) {
            return split[0];
        }
        return "其他";
    }


    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {

        // 尽可能不依赖第三方

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        int idx = ip.indexOf(",");

        if (idx != -1) {
            //如果存在多个ip 取第一个
            ip = ip.substring(0, idx);
        }

        return ip;
    }
}
