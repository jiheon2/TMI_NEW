package kopo.poly.service;

import kopo.poly.dto.NoticeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IFileService {
    void upload(String image, NoticeDTO pDTO, MultipartFile mf) throws Exception;
}
