package kopo.poly.persistance.mapper;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.TraderDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICustomerMapper {
    int insertCustomer(CustomerDTO pDTO) throws Exception;

    CustomerDTO getLogin(CustomerDTO pDTO) throws Exception;

    CustomerDTO getUserIdExists(CustomerDTO pDTO) throws Exception;


    CustomerDTO getUserInfo(CustomerDTO pDTO) throws Exception;
    int changeCustomer(CustomerDTO pDTO) throws Exception;
    int changePw(CustomerDTO pDTO) throws Exception;
}
