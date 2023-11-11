package kopo.poly.controller;

import kopo.poly.dto.MarketDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.IMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/market")
@RequiredArgsConstructor
public class MarketController {

    private final IMarketService marketService;
    @GetMapping(value = "/list")
    @ResponseBody
    public List<String> getMarketList(HttpServletRequest request)
            throws Exception {
        log.info(this.getClass().getName() + ".list Start!");

        String nm = request.getParameter("shopCode");
        List<MarketDTO> rList = marketService.getMarketList(nm);
        if (rList == null) rList = new ArrayList<>();

        List<String> list = new ArrayList<>();

        for(MarketDTO dto : rList) {
            String text = dto.getName() + "[" + dto.getAddr() + "]";
            list.add(text);
        }

        log.info(this.getClass().getName() + ".list End!");
        return list;
    }
    @GetMapping(value = "/marker")
    @ResponseBody
    public List<String> marker(HttpServletRequest request)
            throws Exception {
        log.info(this.getClass().getName() + ".marker Start!");

        String nm = request.getParameter("shopCode");
        List<MarketDTO> rList = marketService.getList(nm);
        if (rList == null) rList = new ArrayList<>();

        List<String> list = new ArrayList<>();

        for(MarketDTO dto : rList) {
            String text = dto.getAddr();
            list.add(text);
        }

        log.info(this.getClass().getName() + ".marker End!");
        return list;
    }
}
