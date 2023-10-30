package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class NoticeDTO {
    private String seq;
    private String title;
    private String noticeYn;
    private String contents;
    private String sender;
    private String readCnt;
    private String image;
    private String regDt;
}
