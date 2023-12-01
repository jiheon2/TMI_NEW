package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerDTO {
    private String customerNumber;
    private String customerName;
    private String phoneNumber;
    private String customerId;
    private String customerPw;
    private String customerEmail;
    private String existsYn;
    private String point;
    private int reward;
}