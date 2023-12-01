package kopo.poly.dto;

import kopo.poly.util.CmmUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentDTO {
    private String applyNum;
    private String bankName;
    private String buyerAddr;
    private String buyerEmail;
    private String buyerPostcode;
    private String buyerTel;
    private String cardName;
    private String cardNumber;
    private String cardQuote;
    private String currency;
    private String customData;
    private String impUid;
    private String name;
    private String paidAmount;
    private String paidAt;
    private String payMethod;
    private String pgProvider;
    private String pgTid;
    private String pgType;
    private String reciptUrl;
    private String status;
    private String success;
}
