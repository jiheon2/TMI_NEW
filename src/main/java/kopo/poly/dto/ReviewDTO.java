package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ReviewDTO {
    private String reviewNumber;
    private String goodsNumber;
    private String customerNumber;
    private String regDt;
    private String contents;
    private String score;
    private String customerId;
    private String traderId;
    private String countScore;

}
