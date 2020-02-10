//package life.showlin.community.mycommunity.comment;
//
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.util.*;
//import java.util.List;
//
///**
// * 常用工具类
// * Created by yymt_hgq on 2018/2/10.
// */
//public class ToolUtils {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ToolUtils.class);
//
//
//
//    /**
//     * 保存文件
//     * @param multipartFiles 上传的文件
//     * @param hostUrl 返回url的头路径
//     * @param path 保存的物理路径的头部
//     * @return
//     */
//    public static R uploadImageFile(MultipartFile[] multipartFiles, String path, String hostUrl) throws Exception{
//        List<String> errorFiles = new ArrayList<>();
//        List<Map<String,String>> uploadFiles = new ArrayList<>();
//        String prefix = "/upload/" + MyDateUtils.format(new Date(), "yyyyMMdd/");
////        try {
//        for (MultipartFile multipartFile : multipartFiles) {
//            String fileName = multipartFile.getOriginalFilename();
//            if (multipartFile.getSize() <= Constant.MAX_FILE_SIZE) {
//                String fileUrl = uploadImageFile(multipartFile.getBytes(),path,prefix, fileName,hostUrl);
//                Map<String,String> data = new HashMap<>();
//                data.put("fileUrl",fileUrl);
//                uploadFiles.add(data);
//            } else {
//                throw new RRException(ResultEnum.UPLOAD_FILE_SIZE_LIMIT);
//                //errorFiles.add(fileName);
//            }
//        }
////        } catch (Exception e) {
////            LOGGER.error("上传文件失败",e);
////            return R.error(ResultEnum.UPLOAD_ERROR);
////        }
//        return R.ok().put("uploadFiles",uploadFiles).put("errorFiles",errorFiles);
//    }
//
//    /**
//     * 保存文件
//     * @param multipartFiles 上传的文件
//     * @param path 保存的物理路径的头部
//     * @return
//     */
//    public static R uploadFile(MultipartFile[] multipartFiles, String path) {
//        // 定义允许上传的文件扩展名
//        HashMap<String, String> extMap = new HashMap<>();
//        extMap.put("media", "swf,flv,mp3,mp4,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,xls,xlsx,docx,doc");
//
//        // 最大文件大小 100M
//        Integer maxSize = Constant.MAX_SOUND_FILE_SIZE;
//
//        // 创建文件夹
//        String midPath = "/upload/sound" +  "/" + MyDateUtils.format(new Date(), "yyyyMMdd/");
//        String savePath = path + midPath;
//
//        File dirFile = new File(savePath);
//        if (!dirFile.exists()) {
//            dirFile.mkdirs();
//        }
//
//        for (MultipartFile multipartFile : multipartFiles) {
//            String fileName = multipartFile.getOriginalFilename();
//            // 检查扩展名
//            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//            if (!Arrays.asList(extMap.get("media").split(",")).contains(fileExt)) {
//                return R.error().put("error", 1).put("message", "上传文件类型错误。\n只允许"+ extMap.get("media") + "格式。");
//            }
//            if (multipartFile.getSize() <= maxSize) {
//                String fileUrl = null;
//                try {
//                    fileUrl = ToolUtils.uploadFileStr(multipartFile.getBytes(),path,midPath, fileName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return R.ok().put("error", 0).put("url",path+fileUrl);
//            } else {
//                return R.error(ResultEnum.UPLOAD_FILE_SIZE_LIMIT);
//            }
//        }
//        return R.ok();
//    }
//
//    /**
//     * 保存文件
//     * @param file 文件
//     * @param topPath 保存的物理路径的头部
//     * @param fileName 文件名
//     * @return
//     */
//    public static Map<String,String> uploadFile(byte[] file, String topPath,String prefix, String fileName) {
//        Map<String,String> map = new HashMap<>();
//        String fileUrl = uploadFileStr(file,topPath,prefix,fileName);
//        map.put("fileUrl",fileUrl);
//        return map;
//    }
//
//
//
//    /**
//     * 保存文件
//     * @param file 文件
//     * @param topPath 保存的物理路径的头部
//     * @param fileName 文件名
//     * @return
//     */
//    public static String uploadFileStr(byte[] file, String topPath,String prefix, String fileName) {
//        String name = renameFile(fileName);
//        FileOutputStream out = null;
//        try {
//            String filePath = topPath+prefix;
//            File targetFile = new File(filePath);
//            if(!targetFile.exists()){
//                targetFile.mkdirs();
//            }
//
//            out = new FileOutputStream(filePath+name);
//            out.write(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RRException(ResultEnum.UPLOAD_ERROR);
//        }finally {
//            if (out != null){
//                try {
//                    out.flush();
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
////        return hostUrl+prefix+name;
//        return prefix+name;
//    }
//
//    /**
//     *
//     * @param file
//     * @param topPath
//     * @param prefix
//     * @param fileName
//     * @param hostUrl
//     * @return
//     */
//    public static String uploadImageFile(byte[] file, String topPath,String prefix, String fileName,String hostUrl) {
//        String name = renameFile(fileName);
//        FileOutputStream out = null;
//        try {
//            String filePath = topPath+prefix;
//            File targetFile = new File(filePath);
//            if(!targetFile.exists()){
//                targetFile.mkdirs();
//            }
//            out = new FileOutputStream(filePath+name);
//            float quality = (1024*1024f) / file.length;
//            BufferedImage bi1 = ImageIO.read(new ByteArrayInputStream(file));
////            InputStream in=new ByteArrayInputStream(file);
//            int width = bi1.getWidth() >= 800?800:bi1.getWidth();
////            Thumbnails.of(new ByteArrayInputStream(file)).width(width).outputQuality(quality>1?1f:quality).toOutputStream(out);
//            //缩放品质
//            float outputQuality = 1f;
//            if(quality<0.5f){//防止品质降低太多
//                outputQuality = 0.8f;
//            }else if(quality<1f){
//                outputQuality = quality;
//            }
//            Thumbnails.of(new ByteArrayInputStream(file)).width(width).outputQuality(outputQuality).toOutputStream(out);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RRException(ResultEnum.UPLOAD_ERROR);
//        }finally {
//            if (out != null){
//                try {
//                    out.flush();
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return prefix+name;
//    }
//
//    public static R deleteFile(String path,String fileUrl){
//        try {
//            new File(path + fileUrl).delete();
//        } catch (Exception e) {
//            String msg = "文件删除失败";
//            LOGGER.error(msg, e);
//            return R.error(ResultEnum.DELETE_ERROR);
//        }
//        return R.ok();
//    }
//    /**
//     * rename file to uuid
//     * @param fileName
//     * @return
//     */
//    public static String renameFile(String fileName){
//        String name = StringUtils.trimToNull(fileName);
//        if (name == null) {
//            name = UUID.randomUUID() + "." + null;
//        } else {
//            name = name.replace('\\', '/');
//            name = name.substring(name.lastIndexOf("/") + 1);
//            int index = name.lastIndexOf(".");
//            String ext = null;
//            if (index >= 0) {
//                ext = StringUtils.trimToNull(name.substring(index + 1));
//            }
//            name = UUID.randomUUID() + "." + (ext == null ? null : (ext));
//        }
//        return name;
//    }
//
//    /**
//     * 判断文件是否是图片类型
//     *
//     * @param imageFile
//     * @return
//     */
//    public static boolean isImage(InputStream imageFile) {
//        try {
//            Image img = ImageIO.read(imageFile);
//            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
//                return false;
//            }
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * 获取保存文件的位置,jar所在目录的路径
//     *
//     * @return
//     */
//    public static String getUplodFilePath() {
//        String path = ToolUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        path = path.substring(1, path.length());
//        try {
//            path = java.net.URLDecoder.decode(path, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        int lastIndex = path.lastIndexOf("/") + 1;
//        path = path.substring(0, lastIndex);
//        File file = new File("");
//        return file.getAbsolutePath() + "/";
//    }
//
//    /**
//     * 小程序用户注册生成邀请码
//     * @return
//     */
//    public static String generateUUID() {
//        return UUID.randomUUID().toString().replaceAll("-", "");
//    }
//
//
//    public static R uploadImageFils(MultipartFile[] multipartFiles, String path, String hostUrl) throws Exception{
//        List<String> errorFiles = new ArrayList<>();
//        List<Map<String,String>> uploadFiles = new ArrayList<>();
//        String prefix = "/upload/" + MyDateUtils.format(new Date(), "yyyyMMdd/");
////        try {
//        for (MultipartFile multipartFile : multipartFiles) {
//            String fileName = multipartFile.getOriginalFilename();
//            if (multipartFile.getSize() <= Constant.MAX_FILE_SIZE) {
//                String fileUrl = uploadOfficeFiles(multipartFile,path,prefix, fileName,hostUrl);
//                Map<String,String> data = new HashMap<>();
//                data.put("fileUrl",fileUrl);
//                uploadFiles.add(data);
//            } else {
//                throw new RRException(ResultEnum.UPLOAD_FILE_SIZE_LIMIT);
//                //errorFiles.add(fileName);
//            }
//        }
////        } catch (Exception e) {
////            LOGGER.error("上传文件失败",e);
////            return R.error(ResultEnum.UPLOAD_ERROR);
////        }
//        return R.ok().put("uploadFiles",uploadFiles).put("errorFiles",errorFiles);
//    }
//
//    public static String uploadOfficeFiles(MultipartFile file, String topPath, String prefix, String fileName, String hostUrl) {
//        String name = renameFile(fileName);
//        FileOutputStream out = null;
//        try {
//            String filePath = topPath+prefix;
//            File targetFile = new File(filePath);
//            if(!targetFile.exists()){
//                targetFile.mkdirs();
//            }
//            out = new FileOutputStream(filePath+name);
//            InputStream in=file.getInputStream();
//            byte buffer[]=new byte[1024];
//            int len=0;
//            while ((len=in.read(buffer))>0){
//                out.write(buffer,0,len);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RRException(ResultEnum.UPLOAD_ERROR);
//        }finally {
//            if (out != null){
//                try {
//
//                    out.flush();
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return prefix+name;
//    }
//
//    /**
//     * 根据执行的全方法名字（含包名及类名）
//     * @param methodName
//     * @return
//     */
//    public static String getModelByMethod(String methodName){
//        //匹配到的模块
//        String result="";
//        if(methodName!=null&&!"".equals(methodName)){
//            List modelList=new ArrayList<String[]>();
//            modelList.add("员工资料_EducationExperienceController");
//            modelList.add("宿舍安排_DormitoryArrangementController");
//            modelList.add("健康状况_HealthConditionController");
//            modelList.add("考勤记录_WorkSignedController");
//            modelList.add("用餐记录_RecordMealController");
//            modelList.add("公告管理_SysNoticeController");
//            modelList.add("请假申请_nomake");
//            modelList.add("加班申请_nomake");
//            modelList.add("出差申请_nomake");
//            modelList.add("计量单位_MeasureUnitController");
//            modelList.add("产品类别_CategoryController");
//            modelList.add("产品管理_PurchaseController");
//            modelList.add("仓库管理_WarehouseController");
//            modelList.add("库存报警设置_InventoryReportController");
//            modelList.add("固定资产_FixedAssetsController");
//            modelList.add("资金账号_AccountInfoController");
//            modelList.add("客户分类_BaseValueController");
//            modelList.add("客户资料_CustomerController");
//            modelList.add("职务_JobController");
//            modelList.add("职称_ProfessionalController");
//            modelList.add("宿舍楼_DormitoryController");
//            modelList.add("宿舍房间_DormitoryRoomController");
//            modelList.add("考勤机_AttendanceMachineController");
//            modelList.add("考勤机规则配置_AttendanceRulesController");
//            modelList.add("角色管理_SysRoleController");
//            modelList.add("权限分配_SysPermissionAssignmentController");
//            modelList.add("部门管理_SysDeptController");
//            modelList.add("数据字典_SysDictController");
//            modelList.add("回收站_SysRecycleBinController");
//            modelList.add("个人信息_EmployeeInfoController");
//            modelList.add("全勤天数设置_AttendanceDayController");
//            modelList.add("考勤规则配置_WorkCheckTimeController");
//            modelList.add("请假历史记录_LeaveHistoryController");
//            modelList.add("请假记录_LeaveRecordController");
//            modelList.add("请假申请表_LeaveRequestController");
//            modelList.add("外出历史记录_OutHistoryController");
//            modelList.add("外出记录_OutRecordController");
//            modelList.add("外出申请表_OutRequestController");
//            modelList.add("加班历史记录_OvertimeHistoryController");
//            modelList.add("加班记录_OvertimeRecordController");
//            modelList.add("加班申请_OvertimeRequestController");
//            for(Object modelAndpath:modelList){
//                String[] nameAndPath= ((String)modelAndpath).split("_");
//                Boolean isContain=false;
//                isContain=methodName.matches(".*"+nameAndPath[1]+".*");
//                if(isContain){
//                    result=nameAndPath[0];
//                }
//            }
//        }
//
//
//
//        return result;
//    }
//
//
//
//
//
//
//
//}
