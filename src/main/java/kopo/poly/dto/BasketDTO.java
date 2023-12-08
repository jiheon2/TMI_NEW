package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasketDTO {
    private String basketNumber;
    private String customerNumber;
    private String goodsNumber;
    private String price;
    private String quantity;
    private String goodsName;
    private String goodsImage;
    private String customerId;
}
