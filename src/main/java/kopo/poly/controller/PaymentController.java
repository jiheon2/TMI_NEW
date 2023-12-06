package kopo.poly.controller;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.PaymentDTO;
import kopo.poly.service.IBasketService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final IBasketService basketService;


    @GetMapping(value = "/paymentInfo")
    public String paymentInfo(HttpSession session, ModelMap model, HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".paymentInfo Start!");

        String customerId = (String) session.getAttribute("SS_ID");
        String type =  CmmUtil.nvl((String) session.getAttribute("SS_TYPE"));
        String applyNum = CmmUtil.nvl(request.getParameter("applyNum"));
        if(!type.equals("Customer") || customerId == null) {
            session.invalidate();
            return  "/customer/login";
        }

        PaymentDTO pDTO = new PaymentDTO();

        pDTO.setApplyNum(applyNum);
        pDTO.setCustomerId(customerId);

        PaymentDTO rDTO = Optional.ofNullable(basketService.paymentInfo(pDTO)).orElseGet(PaymentDTO::new);
        log.info(rDTO.toString());

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".paymentInfo End!");
        return "/payment/paymentInfo";
    }
    @GetMapping(value = "/paymentList")
    public String paymentList(HttpSession session, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".paymentList Start!");

        String customerId = (String) session.getAttribute("SS_ID");
        String type =  CmmUtil.nvl((String) session.getAttribute("SS_TYPE"));
        if(!type.equals("Customer") || customerId == null) {
            session.invalidate();
            return  "/customer/login";
        }

        PaymentDTO pDTO = new PaymentDTO();

        pDTO.setCustomerId(customerId);

        List<PaymentDTO> rList = Optional.ofNullable(basketService.getPayment(pDTO)).orElseGet(ArrayList::new);
        log.info(rList.toString());

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".paymentList End!");
        return "/payment/paymentList";
    }
}