package kopo.poly.controller;

import kopo.poly.dto.CouponDTO;
import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.ICouponService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coupon")
public class CouponController {

    private final ICouponService couponService;
    @GetMapping(value = "/coupon")
    public String couponPage(HttpSession session, Model model) throws Exception {
        log.info("coupon start!");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String type =  CmmUtil.nvl((String) session.getAttribute("SS_TYPE"));
        if(!type.equals("Customer") || customerId == null) {
            session.invalidate();
            return  "/customer/login";
        }

        CouponDTO pDTO = new CouponDTO();
        pDTO.setCustomerId(customerId);

        CustomerDTO cDTO = new CustomerDTO();
        cDTO.setCustomerId(customerId);

        CouponDTO rDTO = couponService.getCouponCount(pDTO);
        CustomerDTO tDTO = couponService.getPoint(cDTO);

        model.addAttribute("rDTO", rDTO);
        model.addAttribute("tDTO", tDTO);

        return "/coupon/coupon";
    }

    @GetMapping(value = "/buyCoupon")
    public String buy1000Coupon(HttpSession session, @RequestParam("point") String point) throws Exception {
        log.info("buyCoupon start");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String type = (String) session.getAttribute("SS_TYPE");
        if(!type.equals("Customer")) {
            session.invalidate();
            return  "/customer/login";
        }

        log.info("customerId : " + customerId);
        log.info("point : " + point);

        CustomerDTO pDTO = new CustomerDTO();
        pDTO.setCustomerId(customerId);
        pDTO.setPoint(point);

        couponService.buyCoupon(pDTO);

        CouponDTO cDTO = new CouponDTO();
        cDTO.setCustomerId(customerId);
        cDTO.setCouponName(point+"원쿠폰");
        cDTO.setDiscountRate(point);
        couponService.updateCoupon(cDTO);

        return "/coupon/coupon";
    }

    @PostMapping(value = "/rouletteCoupon")
    public void rouletteCoupon(HttpSession session, @RequestParam("point") String point) throws Exception {
        log.info("rouletteCoupon Start");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info("customerId : " + customerId);
        log.info("point : " + point);

        CouponDTO cDTO = new CouponDTO();
        cDTO.setCustomerId(customerId);
        cDTO.setCouponName(point+"원쿠폰");
        cDTO.setDiscountRate(point);
        couponService.updateCoupon(cDTO);
    }

    @PostMapping(value = "/updatePoint")
    public String updatePoint(HttpSession session, @RequestParam("point") String point) throws Exception {
        log.info("updatePoint start");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info("customerId : " + customerId);
        log.info("point : " + point);

        CustomerDTO pDTO = new CustomerDTO();
        pDTO.setCustomerId(customerId);
        pDTO.setPoint(point);
        couponService.roulettePoint(pDTO);

        return "/coupon/coupon";
    }

    @GetMapping(value = "/couponInfo")
    public String getCouponCount(HttpSession session, Model model) throws Exception {
        log.info("getCustomerCouponCount start");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String type =  CmmUtil.nvl((String) session.getAttribute("SS_TYPE"));
        if(!type.equals("Customer") || customerId == null) {
            session.invalidate();
            return  "/customer/login";
        }

        CouponDTO pDTO = new CouponDTO();
        pDTO.setCustomerId(customerId);

        List<CouponDTO> rList = couponService.getCustomerCouponCount(pDTO);

        model.addAttribute("rList", rList);

        return "/coupon/couponInfo";
    }
}
