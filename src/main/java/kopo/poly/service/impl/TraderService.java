package kopo.poly.service.impl;

import kopo.poly.dto.TraderDTO;
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

    /* 상인 회원가입 코드 */
    @Override
    public TraderDTO getLogin(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getLogin Start!");


        TraderDTO rDTO = Optional.ofNullable(traderMapper.getLogin(pDTO)).orElseGet(TraderDTO::new);

        log.info(this.getClass().getName() + ".getLogin Start!");
        return rDTO;
    }

    /* ID 중복확인 코드 */
    @Override
    public TraderDTO getTraderIdExists(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getTraderIdExists Start!");

        TraderDTO rDTO = traderMapper.getTraderIdExists(pDTO);

        log.info(this.getClass().getName() + ".getTraderIdExists End!");
        return rDTO;
    }

    /* 상인 정보 조회 코드 */
    @Override
    public TraderDTO getTraderInfo(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getTraderInfo Start!");


        TraderDTO rDTO = Optional.ofNullable(traderMapper.getTraderInfo(pDTO)).orElseGet(TraderDTO::new);

        log.info(this.getClass().getName() + ".getTraderInfo Start!");
        return rDTO;
    }

    /* 사업자 등록번호 중복 확인 코드 */
    @Override
    public TraderDTO getBusinessNumberExists(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getBusinessNumExists Start!");

        TraderDTO rDTO = traderMapper.getBusinessNumberExists(pDTO);

        log.info(this.getClass().getName() + ".getBusinessNumExists End!");
        return rDTO;
    }

    /* 상인 회원가입 */
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

    /* 상인 정보 변경 */
    @Override
    public int updateTraderInfo(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateTraderInfo Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = traderMapper.updateTraderInfo(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".updateTraderInfo Start!");
        return res;
    }

    /* 비밀번호 변경 */
    @Override
    public int updateTraderPw(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateTraderPw Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = traderMapper.updateTraderPw(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".updateTraderPw Start!");
        return res;
    }
}
