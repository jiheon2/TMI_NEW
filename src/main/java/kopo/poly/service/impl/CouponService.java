package kopo.poly.service.impl;

import kopo.poly.dto.CouponDTO;
import kopo.poly.dto.CustomerDTO;
import kopo.poly.persistance.mapper.ICouponMapper;
import kopo.poly.service.ICouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService implements ICouponService {
    private final ICouponMapper couponMapper;
    @Override
    public int buyCoupon(CustomerDTO pDTO) throws Exception {
        log.info("buyCoupon start!");

        int res = 0;

        couponMapper.buyCoupon(pDTO);

        return res;
    }

    @Override
    public int updateCoupon(CouponDTO cDTO) throws Exception {
        log.info("updateCoupon start");

        int res = 0;

        couponMapper.updateCoupon(cDTO);

        return res;
    }

    @Override
    public void roulettePoint(CustomerDTO pDTO) throws Exception {
        log.info("roulettePoint start");

        couponMapper.roulettePoint(pDTO);
    }

    @Override
    public CouponDTO getCouponCount(CouponDTO pDTO) throws Exception {
        log.info("getCouponCount start");

        return couponMapper.getCouponCount(pDTO);
    }

    @Override
    public CustomerDTO getPoint(CustomerDTO cDTO) throws Exception {
        log.info("getPoint start");

        return couponMapper.getPoint(cDTO);
    }

    @Override
    public List<CouponDTO> getCustomerCouponCount(CouponDTO pDTO) throws Exception {
        log.info("getCustomerCouponCount start");

        return couponMapper.getCustomerCouponCount(pDTO);
    }
    @Override
    public int deleteCoupon(CouponDTO pDTO) throws Exception {
        log.info("deleteCoupon start!");

        int res = 0;

        couponMapper.deleteCoupon(pDTO);

        return res;
    }
}
