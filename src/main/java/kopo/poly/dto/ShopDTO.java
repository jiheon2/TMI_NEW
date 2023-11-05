package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShopDTO {
    private String cname;
    private String type;
    private String loc;
    private String des;
    private String name;
    private String image;
    private String tid;
    private String count;
    private String reviewCount;
    private String reserveCount;
    private String buyCount;
    private String reserveStop;
}
