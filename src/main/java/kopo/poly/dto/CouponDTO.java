package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CouponDTO {
    private String couponCode;
    private String customer;
    private String couponName;
    private String useYn;
    private String discount;
}
