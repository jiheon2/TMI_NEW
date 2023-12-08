package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collation = "comment")
public class CommentDTO {
    private String customerId; // 댓글 id
    private String commentNumber; // 댓글번호
    private String postNumber; // 게시글 번호
    private String depth; // 대댓글 확인
    private String regDt; // 등록일
    private String contents; // 댓글 내용
}
