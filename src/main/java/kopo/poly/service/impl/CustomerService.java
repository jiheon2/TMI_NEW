package kopo.poly.service.impl;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.persistance.mapper.ICustomerMapper;
import kopo.poly.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {
    private final ICustomerMapper customerMapper;

    /* 로그인 정보 조회 코드 */
    @Override
    public CustomerDTO getLogin(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getLogin Start!");

        log.info(pDTO.toString());

        CustomerDTO rDTO = Optional.ofNullable(customerMapper.getLogin(pDTO)).orElseGet(CustomerDTO::new);

        log.info(this.getClass().getName() + ".getLogin Start!");
        return rDTO;
    }

    /* 소비자 ID 중복확인 코드 */
    @Override
    public CustomerDTO getCustomerIdExists(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getCustomerIdExists Start!");

        CustomerDTO rDTO = customerMapper.getCustomerIdExists(pDTO);

        log.info(this.getClass().getName() + ".getCustomerIdExists End!");
        return rDTO;
    }

    /* 소비자 정보 등록 코드 */
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

    /* 소비자 정보 조회 코드 */
    @Override
    public CustomerDTO getCustomerInfo(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getCustomerInfo Start!");


        CustomerDTO rDTO = Optional.ofNullable(customerMapper.getCustomerInfo(pDTO)).orElseGet(CustomerDTO::new);

        log.info(this.getClass().getName() + ".getCustomerInfo Start!");
        return rDTO;
    }

    /* 소비자 정보 수정 코드 */
    @Override
    public int updateCustomerInfo(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateCustomerInfo Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = customerMapper.updateCustomerInfo(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".updateCustomerInfo Start!");
        return res;
    }

    /* 소비자 비밀번호 수정 코드 */
    @Override
    public int updateCustomerPw(CustomerDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateCustomerPw Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = customerMapper.updateCustomerPw(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".updateCustomerPw Start!");
        return res;
    }

    @Override
    public int pointReward(CustomerDTO pDTO) throws Exception {
        log.info("pointReward Start!");

        return customerMapper.pointReward(pDTO);
    }

    @Override
    public void resetReward() throws Exception {
        log.info("resetReward start");
        customerMapper.resetReward();
    }
}
