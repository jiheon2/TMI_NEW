package kopo.poly.controller;

import kopo.poly.dto.*;
import kopo.poly.service.*;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final IReservationService reservationService;
    private final ITraderService traderService;
    private final IGoodsService goodsService;
    private final IShopService shopService;
    private final ICustomerService customerService;

    // 예약 페이지 이동코드
    // 구현중
    @GetMapping(value = "/reservation/reservMng")
    public String reservMng(HttpSession session, Model model) throws Exception {
        log.info(this.getClass().getName() + ".reservMng Start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        GoodsDTO pDTO = new GoodsDTO();
        pDTO.setTraderId(traderId);

        List<GoodsDTO> rList = goodsService.getGoodsList(pDTO);

        model.addAttribute("rList", rList);

        return "/reservation/reservMng";
    }

    /* 일정 목록 불러오기 */
    @ResponseBody
    @GetMapping(value = "/reservation/getReservationList")
    public ResponseEntity<?> getReservationList() throws Exception {
        List<ReservationDTO> rList = reservationService.getReservationList();

        JSONArray eventsArray = new JSONArray();

        for (ReservationDTO reservation : rList) {
            JSONObject eventObject = new JSONObject();
            eventObject.put("title", "일정");
            eventObject.put("start", reservation.getReservationDate());

            eventsArray.put(eventObject);
        }

        JSONObject response = new JSONObject();
        response.put("events", eventsArray);

        log.info("response : " + response);

        return ResponseEntity.ok().body(response.toString());
    }

    @ResponseBody
    @PostMapping(value = "/reservation/getReservationInfo")
    public List<ReservationDTO> getReservationInfo(@RequestBody String selectDate, Model model) throws Exception {
        log.info(this.getClass().getName() + ".getReservationInfo Start!");

        log.info("selectDate : " + selectDate);

        String date = selectDate.substring(0, selectDate.length() - 1);
        log.info("date : " + date);

        ReservationDTO pDTO = new ReservationDTO();
        pDTO.setReservationDate(date);

        List<ReservationDTO> rList = reservationService.getTodayReservationList(pDTO);

        log.info("rList : " + rList);

        ReservationDTO rDTO = rList.get(0);
        log.info("값1 : " + rDTO.getReservationContents());
        log.info("값2 : " + rDTO.getReservationDate());
        log.info("값3 : " + rDTO.getReservationPrice());
        log.info("값4 : " + rDTO.getReservationNumber());

        return rList;
    }

    @ResponseBody
    @PostMapping(value = "/reservation/insertReservationInfo")
    public MsgDTO insertReservationInfo(HttpServletRequest request, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + ".insertReservationInfo Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;


        try {
            String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String customerName = CmmUtil.nvl(request.getParameter("customerName"));
            String reservationContents = CmmUtil.nvl(request.getParameter("reservationContents"));
            String reservationPrice = CmmUtil.nvl(request.getParameter("reservationPrice"));
            String reservationDate = CmmUtil.nvl(request.getParameter("selectedDateInput"));
            String customerPhoneNumber = CmmUtil.nvl(request.getParameter("customerPhoneNumber"));
            String goodsNumber = CmmUtil.nvl(request.getParameter("goodsName"));

            log.info("traderName : " + traderId);
            log.info("customerName : " + customerName);
            log.info("reservationContents : " + reservationContents);
            log.info("reservationPrice : " + reservationPrice);
            log.info("reservationDate : " + reservationDate);
            log.info("customerPhoneNumber : " + customerPhoneNumber);

            TraderDTO tDTO = new TraderDTO();
            tDTO.setTraderId(traderId);
            TraderDTO traderInfo = traderService.getTraderInfo(tDTO);

            GoodsDTO gDTO = new GoodsDTO();
            gDTO.setTraderId(goodsNumber);
            GoodsDTO goodsInfo = goodsService.getGoodsInfo(gDTO);

            ShopDTO sDTO = new ShopDTO();
            sDTO.setTraderId(traderId);
            ShopDTO shopInfo = shopService.getShopInfo(sDTO);

            CustomerDTO cDTO = new CustomerDTO();
            cDTO.setPhoneNumber(customerPhoneNumber);
            CustomerDTO customerInfo = customerService.getCustomerInfo(cDTO);

            ReservationDTO pDTO = new ReservationDTO();
            pDTO.setTraderId(traderId);
            pDTO.setTraderName(traderInfo.getTraderName());
            pDTO.setCustomerName(customerName);
            pDTO.setReservationContents(reservationContents);
            pDTO.setReservationPrice(reservationPrice);
            pDTO.setReservationDate(reservationDate);
            pDTO.setGoodsNumber(goodsNumber);
            pDTO.setGoodsName(goodsInfo.getGoodsName());
            pDTO.setMarketNumber(shopInfo.getMarketNumber());
            pDTO.setShopNumber(shopInfo.getShopNumber());
            pDTO.setCustomerId(customerInfo.getCustomerId());
            pDTO.setCustomerPhoneNumber(customerPhoneNumber);

            reservationService.insertReservationInfo(pDTO);
            msg = "등록되었습니다";
            res = 1;

        } catch (Exception e) {
            msg = "실패하였습니다 : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(dto.toString());
            log.info(this.getClass().getName() + ".insertReservationInfo End!");
        }

        return dto;
    }

    @ResponseBody
    @PostMapping(value = "/reservation/updateReservationInfo")
    public MsgDTO updateReservationInfo(HttpServletRequest request, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + ".updateReservationInfo Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String traderName = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String customerName = CmmUtil.nvl(request.getParameter("updateCustomerName"));
            String reservationContents = CmmUtil.nvl(request.getParameter("updateReservationContents"));
            String reservationPrice = CmmUtil.nvl(request.getParameter("updateReservationPrice"));
            String reservationDate = CmmUtil.nvl(request.getParameter("selectedNewDateInput"));
            String reservationNumber = CmmUtil.nvl(request.getParameter("updateReservationNumber"));

            log.info("traderName : " + traderName);
            log.info("customerName : " + customerName);
            log.info("reservationContents : " + reservationContents);
            log.info("reservationPrice : " + reservationPrice);
            log.info("reservationDate : " + reservationDate);
            log.info("reservationNumber : " + reservationNumber);

            ReservationDTO pDTO = new ReservationDTO();
            pDTO.setTraderName(traderName);
            pDTO.setCustomerName(customerName);
            pDTO.setReservationContents(reservationContents);
            pDTO.setReservationPrice(reservationPrice);
            pDTO.setReservationDate(reservationDate);
            pDTO.setReservationNumber(reservationNumber);

            reservationService.updateReservationInfo(pDTO);
            msg = "등록되었습니다";
            res = 1;

        } catch (Exception e) {
            msg = "실패하였습니다 : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(dto.toString());
            log.info(this.getClass().getName() + ".updateReservationInfo End!");
        }

        return dto;
    }

    @ResponseBody
    @PostMapping(value = "/reservation/deleteReservation")
    public MsgDTO reservationDelete(HttpSession session, HttpServletRequest request) {

        log.info(this.getClass().getName() + ".reservationDelete Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String reservationNumber = CmmUtil.nvl(request.getParameter("reservationNumber"));

            log.info("traderId : " + traderId);
            log.info("reservationNumber : " + reservationNumber);

            if (traderId.isEmpty()) {
                msg = "로그인 해주시길 바랍니다.";
            } else {
                ReservationDTO pDTO = new ReservationDTO();
                pDTO.setReservationNumber(reservationNumber);
                reservationService.deleteReservationInfo(pDTO);

                msg = "삭제되었습니다.";
                res = 1;
            }
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);
            log.info(this.getClass().getName() + ".reservationDelete End!");
        }
        return dto;
    }
}