package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDTO {
    private String couponNumber;
    private String customerNumber;
    private String couponName;
    private String discountRate;
    private String statement;
}
