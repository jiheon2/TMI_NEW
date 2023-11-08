package kopo.poly.service;

import kopo.poly.dto.ReviewDTO;
import kopo.poly.dto.TraderDTO;

import java.util.List;

public interface ITraderService {
    TraderDTO getTraderIdExists(TraderDTO pDTO) throws Exception;
    TraderDTO getBusinessNumExists(TraderDTO pDTO) throws Exception;
    TraderDTO getLogin(TraderDTO pDTO) throws Exception;
    TraderDTO getTraderInfo(TraderDTO pDTO) throws Exception;
    int insertTrader(TraderDTO pDTO) throws Exception;
    int changeTrader(TraderDTO pDTO) throws Exception;
    int changePw(TraderDTO pDTO) throws Exception;
}
