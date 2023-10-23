package poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDTO {
    private String couponCode;
    private String user;
    private String couponName;
    private String useYn;
    private String discount;
}
