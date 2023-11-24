package kopo.poly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coupon")
public class CouponController {
    @GetMapping(value = "/coupon")
    public String couponPage() {
        log.info("start!");
        return "/coupon/coupon";
    }

}
