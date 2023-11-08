package kopo.poly.service.impl;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.TraderDTO;
import kopo.poly.persistance.mapper.ICustomerMapper;
import kopo.poly.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {
    private final ICustomerMapper customerMapper;

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
