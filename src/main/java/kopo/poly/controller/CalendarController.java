package kopo.poly.controller;

import kopo.poly.dto.ReserveDTO;
import kopo.poly.dto.ShopDTO;
import kopo.poly.service.IShopService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/calender")
public class CalendarController {

    private final IShopService shopService;

    @GetMapping("/get")
    @ResponseBody
    public List<ShopDTO> getEvents(HttpSession session) throws Exception{
        log.info(this.getClass().getName() + ".getEvents Start!");

        String tid = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        ShopDTO pDTO = new ShopDTO();
        pDTO.setTid(tid);
        List<ShopDTO> events = shopService.getCalender(pDTO);

        log.info(events.toString());

        log.info(this.getClass().getName() + ".getEvents End!");
        return events;
    }
    @GetMapping("/list")
    @ResponseBody
    public List<ReserveDTO> getList(HttpSession session, HttpServletRequest request) throws Exception{
        log.info(this.getClass().getName() + ".getList Start!");

        String tid = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String date = CmmUtil.nvl(request.getParameter("date"));
        log.info(date);
        ReserveDTO pDTO = new ReserveDTO();
        pDTO.setTid(tid);
        pDTO.setState("1");
        pDTO.setDate(date);
        List<ReserveDTO> events = shopService.goodsBuyInfo(pDTO);
        if (events == null) events = new ArrayList<>();

        log.info(events.toString());

        log.info(this.getClass().getName() + ".getList Start!");
        return events;
    }
}
