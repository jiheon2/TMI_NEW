package poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraderDTO {
    private String businessNum;
    private String shopCode;
    private String name;
    private String pn;
    private String id;
    private String pw;
    private String existsYn; // 회원가입 시, 중복방지를 위한 변수(회원이 존재하면 Y)
}
