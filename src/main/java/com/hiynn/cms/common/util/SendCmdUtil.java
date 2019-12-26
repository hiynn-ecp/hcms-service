package com.hiynn.cms.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liuhy
 * @Date: 2019/11/29 15:05
 */
@Slf4j
public class SendCmdUtil {


    public static Map<String, Object> sendCommand(String command) throws Exception {
        log.info("发送命令 | 命令为:{}", command);
        Process process = Runtime.getRuntime().exec(command);
        Map<String, Object> msg = getMsg(process);
        return msg;
    }

    public static Map<String, Object> sendCommand(String[] command) throws Exception {
        for (String s : command) {
            log.info("发送命令 | 命令为:{}", s);
        }
        Process process = Runtime.getRuntime().exec(command);
        Map<String, Object> msg = getMsg(process);
        return msg;
    }

    public static Map<String, Object> getMsg(Process process) throws IOException, InterruptedException {
        Map<String, Object> map = Maps.newHashMap();
        List<String> errorList = Lists.newArrayList();
        List<String> successList = Lists.newArrayList();

        @Cleanup InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String sucessmsg = null;
        while ((sucessmsg = bufferedReader.readLine()) != null) {
            if (StringUtils.isNotBlank(sucessmsg)) {
                log.info("发送命令返回正确信息 | {}", sucessmsg);
                successList.add(sucessmsg);
            }
        }
        @Cleanup InputStream errorStream = process.getErrorStream();
        BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(errorStream));
        String errorsmsg = null;
        while ((errorsmsg = errorBufferedReader.readLine()) != null) {
            if (StringUtils.isNotBlank(errorsmsg)) {
                errorList.add(errorsmsg);
                log.info("发送命令返回错误信息 | {}", errorsmsg);
            }
        }
        map.put("success", successList);
        map.put("error", errorList);
        int i = process.waitFor();
        map.put("code", i);
        log.info("返回信息:{}", i);
        return map;
    }


}
