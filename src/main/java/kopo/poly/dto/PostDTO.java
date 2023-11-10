package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private String postNumber;
    private String title;
    private String contents;
    private String readCount;
    private String regDt;
    private String chgDt;
    private String customerId;
    private String customerNumber;
    private String image;
}
