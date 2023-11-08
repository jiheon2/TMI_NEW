package kopo.poly.persistance.mapper;

import kopo.poly.dto.ReviewDTO;
import kopo.poly.dto.TraderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ITraderMapper {
    int insertTrader(TraderDTO pDTO) throws Exception;

    int changeTrader(TraderDTO pDTO) throws Exception;

    int changePw(TraderDTO pDTO) throws Exception;

    TraderDTO getLogin(TraderDTO pDTO) throws Exception;

    TraderDTO getTraderInfo(TraderDTO pDTO) throws Exception;

    TraderDTO getBusinessNumExists(TraderDTO pDTO) throws Exception;

    TraderDTO getTraderIdExists(TraderDTO pDTO) throws Exception;
}
