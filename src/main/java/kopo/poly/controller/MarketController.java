package kopo.poly.controller;

import kopo.poly.dto.MarketDTO;
import kopo.poly.service.IMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<String> list(HttpServletRequest request)
            throws Exception {
        log.info(this.getClass().getName() + ".list Start!");

        String nm = request.getParameter("shopCode");
        List<MarketDTO> rList = marketService.getList(nm);
        if (rList == null) rList = new ArrayList<>();

        List<String> list = new ArrayList<>();

        for(MarketDTO dto : rList) {
            String text = dto.getMarketName() + "[" + dto.getMarketLocation() + "]";
            list.add(text);
        }

        log.info(this.getClass().getName() + ".list End!");
        return list;
    }
}
