package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CouponDTO {
    private String couponNumber;
    private String customerNumber;
    private String couponName;
    private String discountRate;
    private String statement;
}
