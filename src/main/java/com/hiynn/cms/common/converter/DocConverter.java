package com.hiynn.cms.common.converter;

import com.hiynn.cms.common.util.ValidatorUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * office 文档转换
 *
 * @author 张朋
 * @date 2019/11/18 15:31
 */
@Component
public class DocConverter {

    private final DocumentConverter documentConverter;

    @Autowired
    public DocConverter(DocumentConverter documentConverter) {
        this.documentConverter = documentConverter;
    }

    /**
     * doc转换为PDF
     *
     * @param srcPath Path
     * @param targetPath Path
     * @return fileName
     * @author 张朋
     * @date 2019/11/18 15:41
     */
    public String docToPdf(String srcPath, String targetPath) {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            throw new RuntimeException("原始文件不存在");
        }
        // 如果还是doc类
        if (ValidatorUtils.isDoc(targetPath)) {
            // 转换后缀
            int dot = targetPath.lastIndexOf('.');
            if (dot != -1) {
                String fuxName = targetPath.substring(0, dot);
                targetPath = fuxName + ".pdf";
            }
        }
        File targetFile = new File(targetPath);
        // 转换
        docToPdf(srcFile, targetFile);
        // 返回文件名
        return targetFile.getName();
    }

    /**
     * doc转换为PDF
     *
     * @param srcFile File
     * @param targetFile File
     * @author 张朋
     * @date 2019/11/18 15:41
     */
    public void docToPdf(File srcFile, File targetFile) {
        try {
            documentConverter.convert(srcFile).to(targetFile).execute();
        } catch (OfficeException e) {
            throw new RuntimeException(e);
        }
    }
}
