package live.ipso.provider.common.utils;

import live.ipso.provider.common.config.Tools ;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

/**
 * 文件操作工具类
 * 文案金上传下载工具
 */
public class FileUtil {

    private final static String IP_PORT = "http://127.0.0.1:9001";
    final private static String BASE_ROOT = System.getProperty("user.dir") + "/graduation-provider-9001/src/main/resources/static/images/";
    /**
     * 文件上传，用法：根据使用情景，调用改文件上传的方法，并将获取到的文件路径存入相应的数据库
     *
     * @param file 用户上传的文件信息
     * @return 返回上传成功后的服务器端文件路径
     */
    public static String upload(MultipartFile file) {

        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        System.out.println(BASE_ROOT + fileName);
        File dest = new File(BASE_ROOT + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回static中的位置
        return IP_PORT + "/images/" + fileName;

    }

    /**
     * 文件下载，用法，传入完整的文件名(包含文件的路径)
     *
     * @param filename 文件名(包含文件名)
     * @return 自定义响应实体
     */
    public static ResponseEntity download(String filename) {

        // filename = "F:\\xiangmu\\cics\\CICS\\代码\\backend\\upload\\2019-11-11\\1573472113234_3461_cs4.jpg";
        // 2. 把文件读到程序
        InputStream io = null;
        ResponseEntity<byte[]> responseEntity = null;
        try {

            io = new FileInputStream(filename);
            filename = URLEncoder.encode(filename, "UTF-8"); // 解决文件名中文乱码问题
            byte[] body = new byte[io.available()];
            io.read(body); // 将body中的数据读到io中

            // 3. 创建响应头
            HttpHeaders httpHeaders = new HttpHeaders();
            // 设置文件以附件的形式下载
            httpHeaders.add("content-Disposition", "attachment;filename=" + filename);

            responseEntity = new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
            return responseEntity;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件删除，用法，传入完整的文件名(包含文件的路径)
     *
     * @param filename 文件名(包含文件名)
     * @return true/false
     */
    public static void delFile(String filename) {

        File file = new File(filename);
        if (file.isFile()) {
            file.delete();// 删除
        } else {
            File[] files = file.listFiles(); // 获取文件
            if (files == null) {
                file.delete();// 删除
            } else {
                for (int i = 0; i < files.length; i++) {// 循环
                    delAllFile(files[i].getAbsolutePath());
                }
                file.delete();// 删除
            }
        }
    }

    /**
     * 删除文件夹
     * @param folderPath 文件夹完整绝对路径 ,"d:/ipso/save"
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径 ,"d:/ipso/save"
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}
