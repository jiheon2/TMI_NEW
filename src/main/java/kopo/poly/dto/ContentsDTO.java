package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentsDTO {
    private String contentsCode;
    private String title;
    private String regDT;
    private String content;
    private String type;
    private String regId;
    private String authYn;
}
