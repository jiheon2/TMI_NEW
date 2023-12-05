package kopo.poly.service.impl;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.GoodsDTO;
import kopo.poly.dto.PaymentDTO;
import kopo.poly.persistance.mapper.IBasketMapper;
import kopo.poly.service.IBasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BasketService implements IBasketService {

    private final IBasketMapper basketMapper;
    @Override
    public int addBasket(BasketDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".addBasket Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = basketMapper.addBasket(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".addBasket Start!");
        return res;
    }
    @Override
    public List<BasketDTO> getBasketList(BasketDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getBasketList start!");

        return basketMapper.getBasketList(pDTO);
    }
    @Override
    public List<PaymentDTO> getPayment(PaymentDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getPayment start!");

        return basketMapper.getPayment(pDTO);
    }
    @Override
    public int insertPayment(PaymentDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertPayment Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = basketMapper.insertPayment(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".insertPayment Start!");
        return res;
    }
    @Override
    public int deleteBuy(BasketDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteBuy Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = basketMapper.deleteBuy(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".deleteBuy Start!");
        return res;
    }
}
