package life.showlin.community.mycommunity.service;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import life.showlin.community.mycommunity.exception.CustomizeException;
import life.showlin.community.mycommunity.exception.impl.CustomizeCodeImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.security.krb5.Config;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/***
 *
 *@author: XIELIN
 *@date: 2020/02/08 00:53
 *@version 1.0
 */
@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;
    @Value("${ucloud.ufile.private-key}")
    private String privateKey;
    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;
    @Value("${ucloud.ufile.region}")
    private String region;
    @Value("${ucloud.ufile.suffix}")
    private String suffix;
    @Value("${ucloud.ufile.expires}")
    private Integer expires;

    public String upload(InputStream fileStream, String mimeType, String fileName){
        String generatedFileName;
        String[] filePaths = fileName.split(".");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeCodeImpl.FILE_UPLOAD_FAIL);
        }
        try {
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
            ObjectConfig objectConfig = new ObjectConfig(region, suffix);
            PutObjectResultBean response = UfileClient.object(objectAuthorization, objectConfig)
                    .putObject(fileStream, mimeType)
                    .nameAs(generatedFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener((bytesWritten, contentLength) -> {
                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(objectAuthorization, objectConfig)
                        .getDownloadUrlFromPrivateBucket(generatedFileName, bucketName, expires)
                        .createUrl();
                return url;
            } else {
                throw new CustomizeException(CustomizeCodeImpl.FILE_UPLOAD_FAIL);
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeCodeImpl.FILE_UPLOAD_FAIL);
        }catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeCodeImpl.FILE_UPLOAD_FAIL);
        }
    }
}

