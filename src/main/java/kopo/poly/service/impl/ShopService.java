package kopo.poly.service.impl;

import kopo.poly.dto.*;
import kopo.poly.persistance.mapper.IShopMapper;
import kopo.poly.service.IShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopService implements IShopService {
    private final IShopMapper shopMapper;
//    @Override
//    public List<ReserveDTO> goodsBuyInfo(ReserveDTO pDTO) throws Exception {
//
//        log.info(this.getClass().getName() + ".goodsBuyInfo start!");
//
//        return shopMapper.goodsBuyInfo(pDTO);
//    }
//    @Override
//    public List<ShopDTO> getCalender(ShopDTO pDTO) throws Exception {
//
//        log.info(this.getClass().getName() + ".getCalender start!");
//
//        return shopMapper.getDateCount(pDTO);
//    }
//    @Override
//    public ShopDTO getCount(ShopDTO pDTO) throws Exception {
//        log.info(this.getClass().getName() + ".getReviewCount Start!");
//
//        ShopDTO rDTO = new ShopDTO();
//
//        rDTO = Optional.ofNullable(shopMapper.getShopInfo(pDTO)).orElseGet(ShopDTO::new);
//        rDTO.setReviewCount(Optional.ofNullable(shopMapper.getReviewCount(pDTO)).orElseGet(String::new));
//        rDTO.setReserveCount(Optional.ofNullable(shopMapper.getReserveCount(pDTO)).orElseGet(String::new));
//        rDTO.setBuyCount(Optional.ofNullable(shopMapper.getBuyCount(pDTO)).orElseGet(String::new));
//        rDTO.setReserveStop(Optional.ofNullable(shopMapper.getReserveStop(pDTO)).orElseGet(String::new));
//
//        log.info(this.getClass().getName() + ".getReviewCount Start!");
//
//        return rDTO;
//    }


//    @Override
//    public void deleteBuy(ReserveDTO pDTO) throws Exception {
//        log.info(this.getClass().getName() + ".deleteBuy start!");
//        shopMapper.deleteBuy(pDTO);
//    }
//    @Override
//    public void acceptBuy(ReserveDTO pDTO) throws Exception {
//        log.info(this.getClass().getName() + ".deleteBuy start!");
//        shopMapper.acceptBuy(pDTO);
//    }




    @Override
    public ShopDTO getShopInfo(ShopDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getShopInfo Start!");


        ShopDTO rDTO = Optional.ofNullable(shopMapper.getShopInfo(pDTO)).orElseGet(ShopDTO::new);

        log.info(this.getClass().getName() + ".getShopInfo Start!");

        return rDTO;
    }

    @Override
    public int insertOrUpdateShop(ShopDTO pDTO) throws Exception {
        int result = 0;
        int res = 0;
            log.info("수정 시작");
            result = shopMapper.updateShop(pDTO);
            if(result == 0) {
                log.info("인설트 시작");
                result = shopMapper.insertShop(pDTO);
            }

        if (result > 0) {
            res = 1;
        }
        return res;
    }

    @Override
    public String getReviewCount(ShopDTO pDTO) throws Exception {
        return null;
    }

    @Override
    public String getReserveCount(ShopDTO pDTO) throws Exception {
        return null;
    }

    @Override
    public String getBuyCount(ShopDTO pDTO) throws Exception {
        return null;
    }

    @Override
    public String getReserveStop(ShopDTO pDTO) throws Exception {
        return null;
    }
}
