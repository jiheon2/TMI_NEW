package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class ReservationDTO {
    private String reservationNumber;
    private String traderName;
    private String customerName;
    private String traderId;
    private String customerId;
    private String reservationContents;
    private String reservationPrice;
    private String reservationDate;
    private String state;
    private String goodsNumber;
    private String marketNumber;
    private String shopNumber;
    private String goodsName;
}
