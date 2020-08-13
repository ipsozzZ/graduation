package live.ipso.provider.controller;


import live.ipso.provider.common.ResponseModel;
import live.ipso.provider.common.utils.FileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作接口类
 *
 * @author ipso
 * @date 2019/11/11
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传接口
     *
     * @param file 上传文件信息
     * @return filePath
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseModel uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String isSuccessPath = FileUtil.upload(file);
        try {
            return ResponseModel.success("上传成功").addExtend("url", isSuccessPath);
        } catch (Exception e) {
            return ResponseModel.fail("上传错误");
        }
    }

    /**
     * 文件下载，需要给出具体的文件名包含文件路径
     *
     * @param filename 文件名
     * @return 自定义响应实体
     */
    @RequestMapping(value = "/download/{filename}", method = RequestMethod.GET)
    public ResponseEntity download(@PathVariable("filename") String filename) {
        ResponseEntity responseEntity = FileUtil.download(filename);
        return responseEntity;
    }
}
