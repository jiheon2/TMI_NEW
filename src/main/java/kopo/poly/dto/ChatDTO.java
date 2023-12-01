package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ChatDTO {
    private String name; // 채팅 보내는 사람
    private String msg; // 채팅 메시지
    private String date; // 발송날짜
}
