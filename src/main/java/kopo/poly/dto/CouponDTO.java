package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD

@Getter
@Setter
=======
import lombok.ToString;

@Getter
@Setter
@ToString
>>>>>>> 20cb4eba8c20306190fd3ba61777e2f273be2918
public class CouponDTO {
    private String couponCode;
    private String user;
    private String couponName;
    private String useYn;
    private String discount;
}
