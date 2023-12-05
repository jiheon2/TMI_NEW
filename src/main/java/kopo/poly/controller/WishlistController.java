package kopo.poly.controller;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.WishListDTO;
import kopo.poly.service.IWishlistService;
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
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(value = "/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final IWishlistService wishlistService;
    // 장바구니 담기
    // 구현완료(11/21)
    @ResponseBody
    @PostMapping(value = "doWishlist")
    public MsgDTO doWishlist(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".doWishlist Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        WishListDTO pDTO = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String goodsNumber = CmmUtil.nvl(request.getParameter("goodsNumber"));
            String goodsName = CmmUtil.nvl(request.getParameter("goodsName"));
            String shopName = CmmUtil.nvl(request.getParameter("shopName"));
            String goodsImage = CmmUtil.nvl(request.getParameter("goodsImage"));
            String type =  CmmUtil.nvl((String) session.getAttribute("SS_TYPE"));
//            if(!type.equals("Customer") || customerId == null) {
//                session.invalidate();
//                msg="소비자 로그인이 필요한 서비스입니다";
//                res = 2;
//                dto.setResult(res);
//                dto.setMsg(msg);
//                return  dto;
//            }

            log.info("customerId : " + customerId);
            log.info("shopName : " + shopName);
            log.info("goodsImage : " + goodsImage);
            log.info("goodsName : " + goodsName);
            log.info("goodsNumber : " + goodsNumber);

            pDTO = new WishListDTO();

            pDTO.setCustomerId(customerId);
            pDTO.setShopName(shopName);
            pDTO.setGoodsImage(goodsImage);
            pDTO.setGoodsName(goodsName);
            pDTO.setGoodsNumber(goodsNumber);

            log.info(pDTO.toString());

            res = wishlistService.addWishlist(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "찜목록에 담았습니다";
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
            log.info(this.getClass().getName() + ".doWishlist End!");
        }
        return dto;
    }
    @ResponseBody
    @PostMapping(value = "deleteWish")
    public MsgDTO deleteWish(HttpSession session,@RequestBody Map<String, Object> requestData) {
        log.info(this.getClass().getName() + ".deleteWish Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            List<String> checkboxes = (List<String>) requestData.get("selectedSeqs");
            log.info("id : " + id);
            log.info("checkboxes : " + checkboxes);

            WishListDTO pDTO = new WishListDTO();
            for (String seq : checkboxes) {
                pDTO.setWishlistNumber(seq);
                pDTO.setCustomerId(id);

                wishlistService.deleteWish(pDTO);
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
            log.info(this.getClass().getName() + ".deleteWish End!");
        }

        return dto;
    }
}
