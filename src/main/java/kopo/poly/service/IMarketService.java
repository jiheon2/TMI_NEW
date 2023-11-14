package kopo.poly.service;

import kopo.poly.dto.MarketDTO;

import java.util.List;

public interface IMarketService {
    void getMarketInfo() throws Exception; // 시장 정보 입력 & 삭제
//    int insertMarket(MarketDTO pDTO) throws Exception; // 시장 정보 입력
//    int deleteMarket() throws Exception; // 시장 정보 삭제
    List<MarketDTO> getMarketList(String nm) throws Exception; // 시장 목록 조회
}
