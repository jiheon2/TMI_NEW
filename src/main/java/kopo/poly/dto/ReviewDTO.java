package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDTO {
    private String name;
    private String rating;
    private String regDt;
    private String contents;
    private String product;
    private String traderId;
    private String seq;
}
