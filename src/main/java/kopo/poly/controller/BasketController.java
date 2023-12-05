package kopo.poly.controller;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.PaymentDTO;
import kopo.poly.dto.ReservationDTO;
import kopo.poly.service.IBasketService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(value = "/basket")
@RequiredArgsConstructor
public class BasketController {

    private final IBasketService basketService;

    // 장바구니 담기
    // 구현완료(11/21)
    @ResponseBody
    @PostMapping(value = "/doBasket")
    public MsgDTO doBasket(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".doBasket Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        BasketDTO pDTO = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String quantity = CmmUtil.nvl(request.getParameter("quantity"));
            String price = CmmUtil.nvl(request.getParameter("price"));
            String goodsName = CmmUtil.nvl(request.getParameter("goodsName"));
            String goodsNumber = CmmUtil.nvl(request.getParameter("goodsNumber"));
            String goodsImage = CmmUtil.nvl(request.getParameter("goodsImage"));
            String type =  CmmUtil.nvl((String) session.getAttribute("SS_TYPE"));
            if(!type.equals("Customer") || customerId == null) {
                session.invalidate();
                msg="소비자 로그인이 필요한 서비스입니다";
                res = 2;
                dto.setResult(res);
                dto.setMsg(msg);
                return  dto;
            }

            log.info("customerId : " + customerId);
            log.info("quantity : " + quantity);
            log.info("price : " + price);
            log.info("goodsImage : " + goodsImage);
            log.info("goodsName : " + goodsName);
            log.info("goodsNumber : " + goodsNumber);

            pDTO = new BasketDTO();

            pDTO.setCustomerId(customerId);
            pDTO.setQuantity(quantity);
            pDTO.setPrice(price);
            pDTO.setGoodsImage(goodsImage);
            pDTO.setGoodsName(goodsName);
            pDTO.setGoodsNumber(goodsNumber);

            log.info(pDTO.toString());

            res = basketService.addBasket(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "장바구니에 담았습니다";
            } else {
                msg = "오류로 인해 실패하였습니다";
            }
        } catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".doBasket End!");
        }
        return dto;
    }
    @ResponseBody
    @PostMapping(value = "insertPayment")
    public MsgDTO insertBasket(@RequestBody Map<String, Object> requestData, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".insertPayment Start!");

        String customerId = (String) session.getAttribute("SS_ID");
        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        PaymentDTO pDTO = null;

        try {

            String applyNum = (String) requestData.get("applyNum");
            String bankName = (String) requestData.get("bankName");
            String buyerAddr = (String) requestData.get("buyerAddr");
            String buyerEmail = (String) requestData.get("buyerEmail");
            String buyerPostcode = (String) requestData.get("buyerPostcode");
            String buyerTel = (String) requestData.get("buyerTel");
            String cardName = (String) requestData.get("cardName");
            String cardNumber = (String) requestData.get("cardNumber");
            String cardQuote = (String) requestData.get("cardQuote");
            String currency = (String) requestData.get("currency");
            String customData = (String) requestData.get("customData");
            String impUid = (String) requestData.get("impUid");
            String name = (String) requestData.get("name");
            String paidAmount = requestData.get("paidAmount").toString();
            String paidAt = requestData.get("paidAt").toString();
            String payMethod = (String) requestData.get("payMethod");
            String pgProvider = (String) requestData.get("pgProvider");
            String pgTid = (String) requestData.get("pgTid");
            String pgType = (String) requestData.get("pgType");
            String reciptUrl = (String) requestData.get("reciptUrl");
            String status = (String) requestData.get("status");
            String success = requestData.get("success").toString();

            Date date = new Date(Long.parseLong(paidAt) * 1000);  // Unix timestamp는 밀리초가 아니라 초 단위이므로 1000을 곱해줍니다.

            SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            String formattedDate = dateFormat.format(date);

            pDTO = new PaymentDTO();

            pDTO.setCustomerId(customerId);
            pDTO.setApplyNum(applyNum);
            pDTO.setBankName(bankName);
            pDTO.setBuyerAddr(buyerAddr);
            pDTO.setBuyerEmail(buyerEmail);
            pDTO.setBuyerPostcode(buyerPostcode);
            pDTO.setBuyerTel(buyerTel);
            pDTO.setCardName(cardName);
            pDTO.setCardNumber(cardNumber);
            pDTO.setCardQuote(cardQuote);
            pDTO.setCurrency(currency);
            pDTO.setCustomData(customData);
            pDTO.setImpUid(impUid);
            pDTO.setName(name);
            pDTO.setPaidAmount(paidAmount);
            pDTO.setPayMethod(payMethod);
            pDTO.setPgProvider(pgProvider);
            pDTO.setPgTid(pgTid);
            pDTO.setPaidAt(formattedDate);
            pDTO.setPgType(pgType);
            pDTO.setReciptUrl(reciptUrl);
            pDTO.setStatus(status);
            pDTO.setSuccess(success);

            log.info(pDTO.toString());

            res = basketService.insertPayment(pDTO);

            log.info("res : " + res);

            if (res == 1) {

                BasketDTO pDTO1 = new BasketDTO();
                List<String> basketNumbers = (List<String>) requestData.get("basketNumbers");
                log.info(basketNumbers.toString());

                for (String seq : basketNumbers) {
                    pDTO1.setBasketNumber(seq);

                    res = basketService.deleteBuy(pDTO1);

                }
                msg = "결제되었습니다";
            } else if (res == 2) {
                msg = "이미 처리된 결제입니다";
            } else {
                msg = "오류로 인해 결제에 실패하였습니다";
            }
        } catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".insertPayment End!");
        }
        return dto;
    }
    @ResponseBody
    @PostMapping(value = "deleteBasket")
    public MsgDTO acceptBuy(HttpSession session,@RequestBody Map<String, Object> requestData) {
        log.info(this.getClass().getName() + ".deleteBasket Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            List<String> checkboxes = (List<String>) requestData.get("selectedSeqs");
            log.info("id : " + id);
            log.info("checkboxes : " + checkboxes);

            BasketDTO pDTO = new BasketDTO();
            for (String seq : checkboxes) {
                pDTO.setBasketNumber(seq);
                pDTO.setCustomerId(id);

                basketService.deleteBuy(pDTO);
            }
            msg = "삭제되었습니다.";

            res = 1;
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);
            log.info(this.getClass().getName() + ".deleteBasket End!");
        }

        return dto;
    }
}