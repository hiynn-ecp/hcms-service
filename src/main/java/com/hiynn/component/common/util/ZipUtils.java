package com.hiynn.component.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip 压缩工具包
 *
 * @author 张朋
 * @date 2019/11/22 14:42
 */
public class ZipUtils {

    private ZipUtils() {
    }

    /**
     * 文件压缩
     *
     * @param srcFile 目录或者单个文件路径
     * @param zipFile 压缩后的ZIP文件路径
     * @throws IOException
     */
    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 文件压缩
     *
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();//记得关闭资源
        }
    }

    /**
     * 压缩到流
     */
    public static void doCompress(String filelName, ZipOutputStream out) throws IOException {
        doCompress(new File(filelName), out);
    }

    /**
     * 压缩到流
     */
    public static void doCompress(File file, ZipOutputStream out) throws IOException {
        doCompress(file, out, "");
    }


    /**
     * 递归压缩一个文件或者文件夹到流
     *
     * @param inFile
     * @param out
     * @param dir
     * @throws IOException
     */
    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    ZipUtils.doCompress(file, out, name);
                }
            }
        } else {
            ZipUtils.doZip(inFile, out, dir);
        }
    }


    /**
     * 到流
     *
     * @param inFile
     * @param out
     * @param dir
     * @throws IOException
     */
    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (StringUtils.isNotBlank(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

    /**
     * 到流带原始文件名称 与 真是物理文件名称
     */
    public static void doZip(String sourceName , File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (StringUtils.isNotBlank(dir)) {
            entryName = dir + "/" + sourceName;
        } else {
            entryName = sourceName;
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

    /**
     * 到流带原始文件名称 与 真是物理文件名称
     */
    /**
     * 下载本地文件集合的压缩包
     * @param fileUrls   本地文件路径的集合
     * @param out
     * @throws IOException
     */
    public static void doZipByFileurls(List<String> fileUrls , OutputStream out) throws IOException {
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(out);
            for (String fileurl : fileUrls) {
                //判断是否存在文件
                if (FileUtil.existLocalFile(fileurl)) {
                    File file = new File(fileurl);
                    doZip(file.getName(),file,zipOut,null);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != zipOut) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}