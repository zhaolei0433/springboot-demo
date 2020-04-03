package com.example.utilsdemo.utils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Enumeration;

/**
 * @author zhaolei
 * Create: 2019/4/26 16:05
 * Modified By:
 * Description: 文件操作工具类
 */
public class FileUtil {
    /**
     * 获取项目目录文件
     *
     * @return
     * @throws FileNotFoundException
     */
    public static File getProjectWorkingSpaceFile() throws Exception {
        File file = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!file.exists()) file = new File("");
        return file;
    }

    /**
     * inputStream 写文件
     *
     * @param inputStream 输入流
     * @param file        文件
     * @return
     * @throws Exception
     */
    public static Boolean outPutFile(InputStream inputStream, File file) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) != -1) {
            fos.write(b, 0, length);
        }
        fos.close();
        return true;
    }

    /**
     * 进入或创建目标文件夹
     *
     * @param parent 父文件路径
     * @param child  子文件路径
     * @return
     */
    public static File createTargetFile(String parent, String child) {
        File targetFile = new File(parent, child);
        if (!targetFile.exists())
            targetFile.mkdirs();
        return targetFile;
    }

    private final static String FILE_SEPARATOR = System.getProperty("file.separator");

    public static boolean unZipFile(String zipFilePath, String unZipDirectory) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            if (zipFile == null) {
                return false;
            } else {
                Enumeration entries = zipFile.getEntries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                    File f = new File(unZipDirectory + FILE_SEPARATOR + zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        if (!f.exists() && !f.mkdirs()) {
                            throw new IOException("Couldn't create directory: " + f);
                        }
                    } else {
                        BufferedInputStream is = null;
                        BufferedOutputStream os = null;
                        try {
                            is = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                            File destDir = f.getParentFile();
                            if (!destDir.exists() && !destDir.mkdirs()) {
                                throw new IOException("Couldn't create dir " + destDir);
                            }
                            os = new BufferedOutputStream(new FileOutputStream(f));
                            int b = -1;
                            while ((b = is.read()) != -1) {
                                os.write(b);
                            }
                        } finally {
                            if (is != null)
                                is.close();
                            if (os != null)
                                os.close();

                        }
                    }
                }
                zipFile.close();
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }


}
