package kopo.poly.controller;

import kopo.poly.dto.ReservationDTO;
import kopo.poly.service.IReservationService;
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
    public String getReservationInfo(@RequestBody String selectDate, Model model) throws Exception {
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

        model.addAttribute("rList", rList);

        return "/reservation/reservMng";
    }
}
