package kopo.poly.service;

import kopo.poly.dto.NoticeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;

public interface IFileService {
    void upload(String image, String folderName, MultipartFile mf) throws Exception;
    String getFileURL(String sender, String fileName) throws Exception;
}
