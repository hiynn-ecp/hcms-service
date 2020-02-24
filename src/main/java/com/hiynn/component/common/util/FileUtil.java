package com.hiynn.component.common.util;

import com.hiynn.component.common.core.exception.CoreException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author xuxitan
 * @description 文件上传下载工具
 * @date 2019/10/25 9:45
 **/
@Slf4j
public class FileUtil {

    /***
     * 描述 单个上传文件（文件名用时间戳命名）
     * @author xuxitan
     * @date 2019/10/25 13:19
     * @param file 上传的文件
     * @param path 上传的路径
     * @return void
     */
    public static String uploadFile(MultipartFile file, String path) {

        //获取文件格式
        String fileFormat = commonUpload(file, path);
        //新的文件名
        String fileName = System.currentTimeMillis() + "." + fileFormat;
        //处理path
        path = dealPath(path);
        //传输
        transfer(file, path, fileName);
        return fileName;
    }

    /***
     * 描述 自定义文件名上传文件(不需要传文件格式)
     * @author xuxitan
     * @date 2019/10/25 13:19
     * @param file 上传的文件
     * @param path 上传的路径
     * @param fileName 自定义的文件名
     * @return void
     */
    public static String customUploadFile(MultipartFile file, String path, String fileName) throws IOException {

        if (StringUtils.isBlank(fileName)) {
            throw new CoreException("文件名不能为空");
        }
        //获取文件格式
        String fileFormat = commonUpload(file, path);
        //拼接文件名
        fileName = fileName + "." + fileFormat;
        //处理path
        path = dealPath(path);

        //传输
        transfer(file, path, fileName);
        return fileName;
    }

    /***
     * 描述 自定义文件名上传文件(需要传文件格式)
     * @author xuxitan
     * @date 2019/10/25 13:19
     * @param file 上传的文件
     * @param path 上传的路径
     * @param fileName 自定义的文件名
     * @param fileFormat 文件的格式
     * @return void
     */
    public static String customUploadFileWithFileFormat(MultipartFile file, String path, String fileName, String fileFormat) throws IOException {

        if (StringUtils.isBlank(fileName)) {
            throw new CoreException("文件名不能为空");
        }
        //拼接文件名
        fileName = fileName + "." + fileFormat;
        //处理path
        path = dealPath(path);

        //传输
        transfer(file, path, fileName);
        return fileName;
    }


    /***
     * 描述 删除文件
     * @author xuxitan
     * @date 2019/10/25 14:48
     * @param file 文件全路径
     * @return void
     */
    public static void deleteFile(String file) {
        File f = new File(file);
        if (!f.exists()) {
            throw new CoreException("文件不存在");
        }
        boolean flag = f.delete();
        log.info("删除文件成功:{}", file);
        if (!flag) {
            throw new CoreException("删除文件失败");
        }
    }


    /**
     * 参数校验,返回文件的格式
     *
     * @param file
     * @param path
     * @return
     */
    private static String commonUpload(MultipartFile file, String path) {
        if (file == null) {
            throw new CoreException("上传文件不能为空");
        }
        if (StringUtils.isBlank(path)) {
            throw new CoreException("上传文件path不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String[] fileStr = originalFilename.split("\\.");

        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", originalFilename);
        log.info("[文件大小] - [{}]", file.getSize());

        File folder = new File(path);

        //如果不存在，创建文件夹
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return fileStr[fileStr.length - 1];
    }


    /**
     * 文件传输
     *
     * @param file
     * @param path
     * @param fileName
     */
    private static void transfer(MultipartFile file, String path, String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path + fileName);
            out.write(file.getBytes());
            out.flush();
            log.info("上传文件成功:{}", fileName);
        } catch (Exception e) {
            log.error("上传文件失败:" + e.getMessage());
            throw new CoreException("上传文件失败:" + file.getOriginalFilename());
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理path
     *
     * @param path
     * @return
     */
    private static String dealPath(String path) {
        //处理path
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        return path;
    }

    /**
     * 描述 下载文件
     *
     * @param request  request
     * @param response response
     * @param dlname   自定义文件名
     * @param filePath 文件路径
     * @author ZhuYL
     * @date 2019/11/19 11:19
     */
    public static void download(HttpServletRequest request, HttpServletResponse response,
                                String dlname, String filePath) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + processFileName(request, dlname));
            OutputStream out = null;
            File file = new File(filePath);
            out = response.getOutputStream();
            FileCopyUtils.copy(new FileInputStream(file), out);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解决IE, Chrome, Firfox下文件名乱码问题
     *
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    public static String processFileName(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        // 获取浏览器类型
        boolean isIE = isMSIE(request);
        if (isIE) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        }
        return fileName;
    }

    /**
     * 判断客户端浏览器类型是否为IE
     */
    public static boolean isMSIE(HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        // IE
        if ((null != agent && -1 != agent.indexOf("MSIE"))
                || (null != agent && -1 != agent.indexOf("Trident"))) {
            return true;
        } else { // Firefox, Chrome等
            return false;
        }
    }

    /**
     *判断本地文件或文件夹是否存在
     * @param filepath 本地全路径
     * @return
     */
    public static boolean existLocalFile(String filepath) {
        File file = new File(filepath);
        if(!file.exists()){
            return false;
        }
        return true;
    }
}
