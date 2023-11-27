package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDTO {
    private String postNumber;
    private String title;
    private String contents;
    private String readCount;
    private String regDt;
    private String chgDt;
    private String customerId;
    private String image;
    private String postType; // 1. 일반 게시글 2.인증 게시글 3.공지사항
}
