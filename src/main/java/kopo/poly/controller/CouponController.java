package kopo.poly.controller;

import kopo.poly.dto.CouponDTO;
import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.ICouponService;
import kopo.poly.service.ICustomerService;
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
    private final ICustomerService customerService;
    @GetMapping(value = "/coupon")
    public String couponPage(HttpSession session, Model model) throws Exception {
        log.info("coupon start!");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String type =  CmmUtil.nvl((String) session.getAttribute("SS_TYPE"));
//        if(!type.equals("Customer") || customerId == null) {
//            session.invalidate();
//            return  "/customer/login";
//        }

        CouponDTO pDTO = new CouponDTO();
        pDTO.setCustomerId(customerId);

        CustomerDTO cDTO = new CustomerDTO();
        cDTO.setCustomerId(customerId);

        CouponDTO rDTO = couponService.getCouponCount(pDTO);
        CustomerDTO tDTO = couponService.getPoint(cDTO);
        CustomerDTO aDTO = customerService.getCustomerInfo(cDTO);

        int todayRotate = aDTO.getRotate();

        model.addAttribute("rDTO", rDTO);
        model.addAttribute("tDTO", tDTO);
        model.addAttribute("todayRotate", todayRotate);

        return "/coupon/coupon";
    }

    @ResponseBody
    @PostMapping(value = "/buyCoupon")
    public MsgDTO buyCoupon(HttpSession session, @RequestParam("point") String point) throws Exception {
        log.info("buyCoupon start");
        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

            log.info("customerId : " + customerId);
            log.info("point : " + point);

            CustomerDTO pDTO = new CustomerDTO();
            pDTO.setCustomerId(customerId);
            pDTO.setPoint(point);

            CouponDTO cDTO = new CouponDTO();
            cDTO.setCustomerId(customerId);
            cDTO.setCouponName(point+"원쿠폰");
            cDTO.setDiscountRate(point);

            String havePoint = customerService.getCustomerInfo(pDTO).getPoint();
            int pointValue = Integer.parseInt(havePoint);

            int CouponPoint = Integer.parseInt(point);

            if (pointValue < CouponPoint) {
                msg = "보유하신 포인트가 쿠폰가격보다 적습니다.";
            } else {
                couponService.buyCoupon(pDTO);
                couponService.updateCoupon(cDTO);
                msg = "쿠폰을 구매하였습니다.";
                res = 1;
            }
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(dto.toString());
            log.info("buyCoupon End");
        }

        return dto;
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
//        if(!type.equals("Customer") || customerId == null) {
//            session.invalidate();
//            return  "/customer/login";
//        }

        CouponDTO pDTO = new CouponDTO();
        pDTO.setCustomerId(customerId);

        List<CouponDTO> rList = couponService.getCustomerCouponCount(pDTO);

        model.addAttribute("rList", rList);

        return "/coupon/couponInfo";
    }
}
