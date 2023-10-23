package poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private String id;
    private String pw;
    private String pn;
    private String name;
    private String socialNumber;
    private String existsYn; // 회원가입 시, 중복방지를 위한 변수(회원이 존재하면 Y)
}
