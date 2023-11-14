package kopo.poly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservController {

    // 예약 페이지 이동코드
    // 구현중
    @GetMapping(value = "/reservation/reservMng")
    public String reservMng() {
        log.info("start");

        return "/reservation/reservMng";
    }
}
