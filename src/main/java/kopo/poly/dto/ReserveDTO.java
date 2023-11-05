package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReserveDTO {
    private String bid;
    private String tid;
    private String date;
    private String name;
    private String price;
    private String seq;
    private String state;
    private String contents;
}
