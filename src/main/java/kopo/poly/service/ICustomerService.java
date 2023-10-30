package kopo.poly.service;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.TraderDTO;

public interface ICustomerService {
    CustomerDTO getUserIdExists(CustomerDTO pDTO) throws Exception;
    CustomerDTO getLogin(CustomerDTO pDTO) throws Exception;
    int insertCustomer(CustomerDTO pDTO) throws Exception;


    CustomerDTO getUserInfo(CustomerDTO pDTO) throws Exception;

    int changeCustomer(CustomerDTO pDTO) throws Exception;

    int changePw(CustomerDTO pDTO) throws Exception;
}
