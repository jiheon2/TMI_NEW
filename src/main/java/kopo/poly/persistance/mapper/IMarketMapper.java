package kopo.poly.persistance.mapper;

import kopo.poly.dto.MarketDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMarketMapper {
    int insertMarket(MarketDTO pDTO) throws Exception; // 시장 정보 입력
    int deleteMarket() throws Exception; // 시장 정보 삭제
    MarketDTO checkMarket(String addr) throws Exception; // 시장 정보 조회
    List<MarketDTO> getMarketList(String nm) throws Exception; // 시장 목록 조회
    List<MarketDTO> getMarketName() throws Exception;
    MarketDTO getMarket(MarketDTO pDTO) throws Exception;

}
