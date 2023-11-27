package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class CommentDTO {
    private String commentNumber; // 댓글번호
    private String postNumber; // 게시글 번호
    private String regId; // 등록자
    private String regDt; // 등록일
    private String contents; // 댓글 내용
}
