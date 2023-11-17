package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {
    private String reservationNumber;
    private String traderName;
    private String customerName;
    private String reservationContents;
    private String reservationPrice;
    private String reservationDate;
    private String reservationState;
}
