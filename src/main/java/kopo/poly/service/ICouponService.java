package kopo.poly.service;

import kopo.poly.dto.CouponDTO;
import kopo.poly.dto.CustomerDTO;

import java.util.List;

public interface ICouponService {
    int buyCoupon(CustomerDTO pDTO) throws Exception;
    int updateCoupon(CouponDTO cDTO) throws Exception;
    void roulettePoint(CustomerDTO pDTO) throws Exception;
    CouponDTO getCouponCount(CouponDTO pDTO) throws Exception;
    CustomerDTO getPoint(CustomerDTO cDTO) throws Exception;
    List<CouponDTO> getCustomerCouponCount(CouponDTO pDTO) throws Exception;
}
