package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReserveDTO {
    private String shopCode;
    private String BusinessNum;
    private String id;
    private String productName;
    private String amount;
    private String price;
    private String date;
    private String content;
}
