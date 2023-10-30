package kopo.poly.service.impl;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.TraderDTO;
import kopo.poly.persistance.mapper.ICustomerMapper;
import kopo.poly.persistance.mapper.ITraderMapper;
import kopo.poly.service.ITraderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraderService implements ITraderService {
    private final ITraderMapper traderMapper;



    @Override
    public TraderDTO getLogin(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getLogin Start!");


        TraderDTO rDTO = Optional.ofNullable(traderMapper.getLogin(pDTO)).orElseGet(TraderDTO::new);

        log.info(this.getClass().getName() + ".getLogin Start!");
        return rDTO;
    }

    @Override
    public TraderDTO getUserIdExists(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserIdExists Start!");

        TraderDTO rDTO = traderMapper.getUserIdExists(pDTO);

        log.info(this.getClass().getName() + ".getUserIdExists End!");
        return rDTO;
    }
    @Override
    public TraderDTO getUserInfo(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserInfo Start!");


        TraderDTO rDTO = Optional.ofNullable(traderMapper.getUserInfo(pDTO)).orElseGet(TraderDTO::new);

        log.info(this.getClass().getName() + ".getUserInfo Start!");
        return rDTO;
    }
    @Override
    public TraderDTO getBusinessNumExists(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getBusinessNumExists Start!");

        TraderDTO rDTO = traderMapper.getBusinessNumExists(pDTO);

        log.info(this.getClass().getName() + ".getBusinessNumExists End!");
        return rDTO;
    }

    @Override
    public int insertTrader(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertTrader Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = traderMapper.insertTrader(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".insertTrader Start!");
        return res;
    }

    @Override
    public int changeTrader(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".changeTrader Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = traderMapper.changeTrader(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".changeTrader Start!");
        return res;
    }
    @Override
    public int changePw(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".changeTrader Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = traderMapper.changePw(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".changeTrader Start!");
        return res;
    }
}
