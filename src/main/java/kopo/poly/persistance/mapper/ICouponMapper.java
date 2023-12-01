package kopo.poly.persistance.mapper;

import kopo.poly.dto.CouponDTO;
import kopo.poly.dto.CustomerDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICouponMapper {
    int buyCoupon(CustomerDTO pDTO) throws Exception;
    int updateCoupon(CouponDTO cDTO) throws Exception;
    void roulettePoint(CustomerDTO pDTO) throws Exception;
    CouponDTO getCouponCount(CouponDTO pDTO) throws Exception;
}
