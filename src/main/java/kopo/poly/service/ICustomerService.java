package kopo.poly.service;

import kopo.poly.dto.CustomerDTO;

public interface ICustomerService {
    CustomerDTO getUserIdExists(CustomerDTO pDTO) throws Exception;
    CustomerDTO getLogin(CustomerDTO pDTO) throws Exception;
    int insertCustomer(CustomerDTO pDTO) throws Exception;
}
