package kopo.poly.controller;

import kopo.poly.dto.MarketDTO;
import kopo.poly.service.IMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

@Controller
@Slf4j
@RequestMapping(value = "/market")
@RequiredArgsConstructor
public class MarketController {

    private final IMarketService marketService;

    // 시장 목록 조회 코드
    // 구현완료(11/13)
    @GetMapping(value = "/list")
    @ResponseBody
    public List<MarketDTO> getMarketList(HttpServletRequest request) //list<MarketDTO>로 변경
            throws Exception {
        log.info(this.getClass().getName() + ".list Start!");

        String nm = request.getParameter("shopCode");
        List<MarketDTO> rList = marketService.getMarketList(nm);
        if (rList == null) rList = new ArrayList<>();

        List<MarketDTO> list = new ArrayList<>();

        for (MarketDTO dto : rList) {  //dto에 각자 담아서 리스트에 담기
            String text = dto.getMarketName() + "[" + dto.getMarketLocation() + "]";
            String lat = dto.getLatitude();
            String lon = dto.getLongitude();
            String num = dto.getMarketNumber();

            MarketDTO rDTO = new MarketDTO();
            rDTO.setLongitude(lon);
            rDTO.setLatitude(lat);
            rDTO.setMarketName(text);
            rDTO.setMarketNumber(num);
            list.add(rDTO);

            log.info("list : " + list); //데이터 값을 잘받아오는지 확인하기
        }

        log.info(this.getClass().getName() + ".list End!");
        return list;
    }

    // 마커 코드
    @GetMapping(value = "/marker")
    @ResponseBody
    public List<MarketDTO> marker() throws Exception {
        log.info(this.getClass().getName() + ".marker Start!");

        List<MarketDTO> pList = marketService.getMarketList("");
        if (pList == null) pList = new ArrayList<>();

        log.info(pList.toString());

        log.info(this.getClass().getName() + ".marker End!");
        return pList;
    }
}
