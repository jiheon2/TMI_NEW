package kopo.poly.persistance.mapper;

import kopo.poly.dto.CouponDTO;
import kopo.poly.dto.CustomerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICouponMapper {
    int buyCoupon(CustomerDTO pDTO) throws Exception;
    int updateCoupon(CouponDTO cDTO) throws Exception;
    void roulettePoint(CustomerDTO pDTO) throws Exception;
    CouponDTO getCouponCount(CouponDTO pDTO) throws Exception;
    CustomerDTO getPoint(CustomerDTO cDTO) throws Exception;
    List<CouponDTO> getCustomerCouponCount(CouponDTO pDTO) throws Exception;
    int deleteCoupon(CouponDTO pDTO) throws Exception;

}
