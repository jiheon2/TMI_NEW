package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WishListDTO {
    private String wishlistNumber;
    private String goodsNumber;
    private String customerNumber;
    private String goodsName;
    private String shopName;
    private String goodsImage;
    private String customerId;
}
