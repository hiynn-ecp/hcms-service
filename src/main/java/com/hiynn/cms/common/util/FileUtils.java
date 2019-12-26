package com.hiynn.cms.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作
 *
 * @author 张朋
 * @date 2019/10/31 18:38
 */
@Slf4j
public class FileUtils {


    /**
     * 文件上传 返回新的文件名称
     * <p>
     * 如没有返回新名称则获取扩展名失败使用默认的
     *
     * @param file
     * @param filePath
     * @return void
     * @author 张朋
     * @date 2019/10/31 18:38
     */
    public static String uploadFile(MultipartFile file, String filePath) {

        if (file == null) {
            throw new RuntimeException("MultipartFile is Null.");
        }

        String ext = null;
        String filename = file.getOriginalFilename();

        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                ext = filename.substring(dot);
            }
        }

        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        if (ext != null) {
            filename = IDUtils.getJavaUUID() + ext;
        }

        try (FileOutputStream fos = new FileOutputStream(filePath + filename)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }


    public static Boolean rm(File file) {
        if (!file.exists()) {
            log.info("{},文件不存在，删除中断退出。", file.toString());
            return false;
        }
        if (file.isDirectory()) {
            log.info("{},目标为文件夹，删除中断退出。", file.toString());
            return false;
        }
        return file.delete();
    }

}
