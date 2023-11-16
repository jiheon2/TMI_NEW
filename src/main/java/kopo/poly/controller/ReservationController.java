package kopo.poly.controller;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.ReservationDTO;
import kopo.poly.service.IReservationService;
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

    // 예약 페이지 이동코드
    // 구현중
    @GetMapping(value = "/reservation/reservMng")
    public String reservMng() throws Exception {
        log.info(this.getClass().getName() + ".reservMng Start!");

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
            String traderName = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String customerName = CmmUtil.nvl(request.getParameter("customerName"));
            String reservationContents = CmmUtil.nvl(request.getParameter("reservationContents"));
            String reservationPrice = CmmUtil.nvl(request.getParameter("reservationPrice"));
            String reservationDate = CmmUtil.nvl(request.getParameter("selectedDateInput"));

            log.info("traderName : " + traderName);
            log.info("customerName : " + customerName);
            log.info("reservationContents : " + reservationContents);
            log.info("reservationPrice : " + reservationPrice);
            log.info("reservationDate : " + reservationDate);

            ReservationDTO pDTO = new ReservationDTO();
            pDTO.setTraderName(traderName);
            pDTO.setCustomerName(customerName);
            pDTO.setReservationContents(reservationContents);
            pDTO.setReservationPrice(reservationPrice);
            pDTO.setReservationDate(reservationDate);

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
}
