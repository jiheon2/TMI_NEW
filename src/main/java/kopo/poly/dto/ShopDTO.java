package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShopDTO {
    private String shopNumber;
    private String shopName;
    private String shopDescription;
    private String marketNumber;
    private String traderId;
    private String image;
    private String traderName;
    private String marketName;
    private String ShopCount;
    private String reviewCount;
    private String reserveCount;
    private String buyCount;
    private String reserveStop;
}
