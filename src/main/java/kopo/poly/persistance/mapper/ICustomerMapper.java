package kopo.poly.persistance.mapper;

import kopo.poly.dto.CustomerDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICustomerMapper {
    int insertCustomer(CustomerDTO pDTO) throws Exception;

    CustomerDTO getLogin(CustomerDTO pDTO) throws Exception;

    CustomerDTO getUserIdExists(CustomerDTO pDTO) throws Exception;
}
