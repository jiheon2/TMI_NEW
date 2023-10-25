package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MarketDTO {
    private String marketCode;
    private String marketName;
    private String location;
    private String homepage;
    private String shopCode;
}
