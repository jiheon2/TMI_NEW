package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShopDTO {
    private String shopCode;
    private String marketCode;
    private String businessNum;
    private String shopLocation;
    private String shopName;
    private String shopType;
    private String shopContent;
    private String image;
}
