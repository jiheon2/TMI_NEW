package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TraderDTO {
    private String traderNumber;
    private String traderId;
    private String traderPw;
    private String businessNumber;
    private String traderName;
    private String phoneNumber;
    private String traderEmail;
    private String shopCode;
    private String account;
    private String bank;
    private String traderNotice;
    private int authNumber;
    private String existsYn;
}
