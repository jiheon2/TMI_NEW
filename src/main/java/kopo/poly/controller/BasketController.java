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
import java.util.ArrayList;
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
    public MsgDTO insertBasket(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".insertPayment Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        PaymentDTO pDTO = null;

        try {

            String applyNum = CmmUtil.nvl(request.getParameter("applyNum"));
            String bankName = CmmUtil.nvl(request.getParameter("bankName"));
            String buyerAddr = CmmUtil.nvl(request.getParameter("buyerAddr"));
            String buyerEmail = CmmUtil.nvl(request.getParameter("buyerEmail"));
            String buyerPostcode = CmmUtil.nvl(request.getParameter("buyerPostcode"));
            String buyerTel = CmmUtil.nvl(request.getParameter("buyerTel"));
            String cardName = CmmUtil.nvl(request.getParameter("cardName"));
            String cardNumber = CmmUtil.nvl(request.getParameter("cardNumber"));
            String cardQuote = CmmUtil.nvl(request.getParameter("cardQuote"));
            String currency = CmmUtil.nvl(request.getParameter("currency"));
            String customData = CmmUtil.nvl(request.getParameter("customData"));
            String impUid = CmmUtil.nvl(request.getParameter("impUid"));
            String name = CmmUtil.nvl(request.getParameter("name"));
            String paidAmount = CmmUtil.nvl(request.getParameter("paidAmount"));
            String paidAt = CmmUtil.nvl(request.getParameter("paidAt"));
            String payMethod = CmmUtil.nvl(request.getParameter("payMethod"));
            String pgProvider = CmmUtil.nvl(request.getParameter("pgProvider"));
            String pgTid = CmmUtil.nvl(request.getParameter("pgTid"));
            String pgType = CmmUtil.nvl(request.getParameter("pgType"));
            String reciptUrl = CmmUtil.nvl(request.getParameter("reciptUrl"));
            String status = CmmUtil.nvl(request.getParameter("status"));
            String success = CmmUtil.nvl(request.getParameter("success"));

            pDTO = new PaymentDTO();

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
            pDTO.setPaidAt(paidAt);
            pDTO.setPgType(pgType);
            pDTO.setReciptUrl(reciptUrl);
            pDTO.setStatus(status);
            pDTO.setSuccess(success);

            log.info(pDTO.toString());

            res = basketService.insertPayment(pDTO);

            log.info("res : " + res);

            if (res == 1) {

                BasketDTO pDTO1 = new BasketDTO();
                String basketNumbers = request.getParameter("basketNumbers");
                log.info(basketNumbers);
                String[] splitStr = basketNumbers.split(",");
                List<String> rList = new ArrayList<>();
                for(int i=0; i<splitStr.length; i++){
                    rList.add(splitStr[i]);
                }
                for (String seq : rList) {
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
