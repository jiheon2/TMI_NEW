package kopo.poly.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.MarketDTO;
import kopo.poly.persistance.mapper.IMarketMapper;
import kopo.poly.service.IMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
@Slf4j
@RequiredArgsConstructor
public class MarketService implements IMarketService {

    private final IMarketMapper marketMapper;
    private final String apiURL = "http://openapi.seoul.go.kr:8088/7a424178626a756e33326366416358/json/ListTraditionalMarket/1/400/";


    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
//    @Scheduled(fixedRate = 60000) // 매분마다 실행
    public void getMarketInfo() throws Exception {

        log.info(this.getClass().getName() + ".getMarket Start!");
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("7a424178626a756e33326366416358","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("ListTraditionalMarket","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("400","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        Map<String, Object> json = new ObjectMapper().readValue(sb.toString(), LinkedHashMap.class);

        Map<String, Object> rMap = (Map<String, Object>) json.get("ListTraditionalMarket");

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("row");

        marketMapper.deleteMarket();

        for(Map<String, Object> market : list) {
            MarketDTO rDTO = new MarketDTO();

            String name = (String) market.get("ITEM_NM");
            String addr = (String) market.get("ITEM_ADDR");

            log.info("name : " + name);
            log.info("addr : " + addr);

            rDTO.setMarketName(name);
            rDTO.setMarketLocation(addr);

            marketMapper.insertMarket(rDTO);
        }

        log.info(this.getClass().getName() + ".getMarket End!");
    }

    @Override
    public List<MarketDTO> getList(String nm) throws Exception {

        log.info(this.getClass().getName() + ".getList Start!");

        return marketMapper.getList(nm);
    }



}

