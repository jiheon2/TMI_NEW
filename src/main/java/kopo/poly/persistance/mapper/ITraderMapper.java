package kopo.poly.persistance.mapper;

import kopo.poly.dto.TraderDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ITraderMapper {
    int insertTrader(TraderDTO pDTO) throws Exception;

    TraderDTO getLogin(TraderDTO pDTO) throws Exception;

    TraderDTO getBusinessNumExists(TraderDTO pDTO) throws Exception;

    TraderDTO getUserIdExists(TraderDTO pDTO) throws Exception;
}
