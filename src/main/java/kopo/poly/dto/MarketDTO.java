package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MarketDTO {
    private String marketNumber;
    private String marketLocation;
    private String marketName;
    private String latitude;
    private String longitude;
    private String PopularMarket;
    private String marketImage;
}
