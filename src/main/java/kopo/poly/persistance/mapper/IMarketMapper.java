package kopo.poly.persistance.mapper;

import jdk.vm.ci.code.site.Mark;
import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MarketDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMarketMapper {
    int insertMarket(MarketDTO pDTO) throws Exception;
    int deleteMarket() throws Exception;
    List<MarketDTO> getList(String nm) throws Exception;
}
