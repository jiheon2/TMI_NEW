package kopo.poly.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import kopo.poly.persistance.mapper.IPostMapper;
import kopo.poly.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService implements IFileService {



    final String endPoint = "https://kr.object.ncloudstorage.com";
    final String regionName = "kr-standard";
    final String accessKey = "xPknMh74eSfrm2DZSp5K";
    final String secretKey = "UtHgsaaWg7eCbFOXRgH2SVPY0mcumpTrriDQs7T6";
    final String bucketName = "tmi";

    // S3 client
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
            .build();
    private final IPostMapper noticeMapper;

    /* 파일 업로드 */
    @Override
    public void upload(String fileName,String folderName, MultipartFile mf) throws Exception {


        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0L);
        objectMetadata.setContentType("application/x-directory");

        File uploadFile = convert(mf, folderName);

        try (InputStream inputStream = mf.getInputStream()){
            s3.putObject(new PutObjectRequest(bucketName, folderName,  inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            System.out.format("Folder %s has been created.\n", folderName);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        // upload local file
        String objectName = folderName + fileName;



        try {
            s3.putObject(new PutObjectRequest(bucketName, objectName, uploadFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            System.out.format("Object %s has been created.\n", objectName);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    /* 파일 URL 조회 */
    @Override
    public String getFileURL(String folderName, String fileName) throws Exception {
        String url = null;
        try {

            url = "https://kr.object.ncloudstorage.com/tmi/" + folderName + fileName;
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        return url;
    }

    /* 이건 뭔지 모름 */
    private File convert(MultipartFile file, String folderName) throws IOException {
        File convertFile = new File(System.getProperty(folderName) + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
            fos.write(file.getBytes());
        }
        return convertFile;
    }

}
