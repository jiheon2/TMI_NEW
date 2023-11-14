package kopo.poly.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    void upload(String image, String folderName, MultipartFile mf) throws Exception; // 파일 업로드
    String getFileURL(String sender, String fileName) throws Exception; // url 정보 조회
}
