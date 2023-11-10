package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ToString
public class MsgDTO {
    private int result; // 성공 : 1 / 실패 : 그 외
    private String msg; // 메시지
}