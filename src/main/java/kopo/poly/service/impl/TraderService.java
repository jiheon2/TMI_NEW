package kopo.poly.service.impl;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.TraderDTO;
import kopo.poly.persistance.mapper.ITraderMapper;
import kopo.poly.service.IMailService;
import kopo.poly.service.ITraderService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraderService implements ITraderService {
    private final ITraderMapper traderMapper;
    private final IMailService mailService;

    /* 상인 회원가입 코드 */
    @Override
    public TraderDTO getLogin(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getLogin Start!");


        TraderDTO rDTO = Optional.ofNullable(traderMapper.getLogin(pDTO)).orElseGet(TraderDTO::new);

        log.info(this.getClass().getName() + ".getLogin Start!");
        return rDTO;
    }

    /* 이메일 찾기 코드 */
    @Override
    public TraderDTO searchEmail(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".emailAuth Start!");

        TraderDTO rDTO = traderMapper.getEmailExists(pDTO);


        String existsYn = CmmUtil.nvl(rDTO.getExistsYn());

        log.info("existsYn : " + existsYn);


            int authNumber = ThreadLocalRandom.current().nextInt(100000,1000000);

            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 확인 인증번호 발송 메일");
            dto.setContents("인증번호는 " + authNumber + " 입니다.");
            dto.setToMail(CmmUtil.nvl(pDTO.getTraderEmail()));

            mailService.doSendMail(dto);

            dto = null;

            rDTO.setAuthNumber(authNumber);

            log.info("authNumber : " + authNumber);


        log.info(this.getClass().getName() + ".emailAuth End!");

        return rDTO;
    }
    @Override
    public TraderDTO searchTraderId(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".searchTraderId Start!");

        TraderDTO rDTO = traderMapper.getTraderId(pDTO);

        log.info(this.getClass().getName() + ".searchTraderId End!");

        return rDTO;
    }

    @Override
    public TraderDTO searchTraderPw(TraderDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".searchTraderPw Start!");

        TraderDTO rDTO = traderMapper.getTraderPw(pDTO);

        log.info(this.getClass().getName() + ".searchTraderPw End!");

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
