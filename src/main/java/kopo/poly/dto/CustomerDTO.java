package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD

@Getter
@Setter
=======
import lombok.ToString;

@Getter
@Setter
@ToString
>>>>>>> 20cb4eba8c20306190fd3ba61777e2f273be2918
public class CustomerDTO {
    private String id;
    private String pw;
    private String pn;
    private String name;
<<<<<<< HEAD
    private String socialNumber;
=======
    private String age;
    private String type;
>>>>>>> 20cb4eba8c20306190fd3ba61777e2f273be2918
    private String existsYn; // 회원가입 시, 중복방지를 위한 변수(회원이 존재하면 Y)
}
