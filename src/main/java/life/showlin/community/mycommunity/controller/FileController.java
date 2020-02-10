package life.showlin.community.mycommunity.controller;

import life.showlin.community.mycommunity.dto.FileDTO;
import life.showlin.community.mycommunity.exception.CustomizeException;
import life.showlin.community.mycommunity.exception.impl.CustomizeCodeImpl;
import life.showlin.community.mycommunity.service.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/07 23:30
 *@version 1.0
 */
@Controller
public class FileController {

    @Autowired
    private UCloudProvider uCloudProvider;
    public static final String UPLOAD_PARENT_PATH = "D:\\idea\\workspace\\my-community\\src\\main\\resources\\static\\uploadImage";
    public static final String UPLOAD_PARENT = "uploadImage";

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");

        //使用ucloud 存储图片
//        try {
//        String generatedFileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        // 保存图片
        String filePath = upload(file);
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
//        fileDTO.setMessage();
        fileDTO.setUrl(filePath);
        return fileDTO;
    }

    private String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String generatedFileName;
        String[] filePaths = fileName.split("\\.");


//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String parentPath = request.getServletContext().getRealPath(UPLOAD_PARENT_PATH);
        if (filePaths.length > 1) {
            generatedFileName = "../" + UPLOAD_PARENT + "/" + UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeCodeImpl.FILE_UPLOAD_FAIL);
        }

        try {
            File targetFile = new File(UPLOAD_PARENT_PATH);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            targetFile = new File(UPLOAD_PARENT_PATH, generatedFileName);
            file.transferTo(targetFile);

//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(UPLOAD_PARENT_PATH + generatedFileName);
//            Files.write(path, bytes);

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeCodeImpl.FILE_UPLOAD_FAIL);
        }
        return generatedFileName;
    }

    protected String getTopUrl(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
