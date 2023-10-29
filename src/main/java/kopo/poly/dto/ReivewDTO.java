package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReivewDTO {
    private String id;
    private String rank;
    private String regDt;
    private String content;
    private String productCode;
}
