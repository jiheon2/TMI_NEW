package kopo.poly.service.impl;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.CustomerDTO;
import kopo.poly.persistance.mapper.ICustomerMapper;
import kopo.poly.service.ICustomerService;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {
    private final ICustomerMapper customerMapper;
    private final IMailService mailService;

    @Override
    public CustomerDTO getLogin(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getLogin Start!");

        log.info(pDTO.toString());

        CustomerDTO rDTO = Optional.ofNullable(customerMapper.getLogin(pDTO)).orElseGet(CustomerDTO::new);

        log.info(this.getClass().getName() + ".getLogin Start!");
        return rDTO;
    }

    @Override
    public CustomerDTO getCustomerIdExists(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getCustomerIdExists Start!");

        CustomerDTO rDTO = customerMapper.getCustomerIdExists(pDTO);

        log.info(this.getClass().getName() + ".getCustomerIdExists End!");
        return rDTO;
    }

    @Override
    public CustomerDTO searchEmail(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".emailAuth Start!");

        CustomerDTO rDTO = customerMapper.getEmailExists(pDTO);


        String existsYn = CmmUtil.nvl(rDTO.getExistsYn());

        log.info("existsYn : " + existsYn);

        if (existsYn.equals("Y")) {
            int authNumber = ThreadLocalRandom.current().nextInt(100000,1000000);

            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 확인 인증번호 발송 메일");
            dto.setContents("인증번호는 " + authNumber + " 입니다.");
            dto.setToMail(CmmUtil.nvl(pDTO.getEmail()));

            mailService.doSendMail(dto);

            dto = null;

            rDTO.setAuthNumber(authNumber);

            log.info("authNumber : " + authNumber);
        }

        log.info(this.getClass().getName() + ".emailAuth End!");

        return rDTO;
    }

    @Override
    public CustomerDTO searchCustomerIdOrPasswordProc(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".searchCustomerIdOrPasswordProc Start!");

        CustomerDTO rDTO = customerMapper.getCustomerId(pDTO);

        log.info(this.getClass().getName() + ".searchCustomerIdOrPasswordProc End!");

        return rDTO;
    }

    @Override
    public CustomerDTO getEmailExists(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".emailAuth Start!");

        CustomerDTO rDTO = customerMapper.getEmailExists(pDTO);

        String existsYn = CmmUtil.nvl(rDTO.getExistsYn());

        log.info("existsYn : " + existsYn);

        if (existsYn.equals("N")) {
            int authNumber = ThreadLocalRandom.current().nextInt(100000,1000000);

            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 중복 확인 인증번호 발송 메일");
            dto.setContents("인증번호는 " + authNumber + " 입니다.");
            dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));
            if(pDTO.getId().isEmpty()) {
                mailService.doSendMail(dto);
            }

            dto = null;

            rDTO.setAuthNumber(authNumber);

            log.info("authNumber : " + authNumber);
        }

        log.info(this.getClass().getName() + ".emailAuth End!");

        return rDTO;
    }
    @Override
    public int insertCustomer(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertCustomer Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = customerMapper.insertCustomer(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".insertCustomer Start!");
        return res;
    }
    @Override
    public CustomerDTO getCustomerInfo(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getCustomerInfo Start!");


        CustomerDTO rDTO = Optional.ofNullable(customerMapper.getCustomerInfo(pDTO)).orElseGet(CustomerDTO::new);

        log.info(this.getClass().getName() + ".getCustomerInfo Start!");
        return rDTO;
    }
    @Override
    public int changeCustomer(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".changeCustomer Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = customerMapper.changeCustomer(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".changeCustomer Start!");
        return res;
    }
    @Override
    public int changePw(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".changeCustomer Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = customerMapper.changePw(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".changeCustomer Start!");
        return res;
    }
}
