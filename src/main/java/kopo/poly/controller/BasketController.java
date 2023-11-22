package kopo.poly.controller;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.MsgDTO;
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
}
