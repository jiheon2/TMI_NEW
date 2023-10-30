package kopo.poly.service;

import kopo.poly.dto.NoticeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;

public interface IFileService {
    void upload(String image, NoticeDTO pDTO, MultipartFile mf) throws Exception;
    URL getFileURL(String fileName) throws Exception;
}
