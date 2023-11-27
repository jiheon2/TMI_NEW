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
    @Override
    public List<ReservationDTO> goodsBuyInfo(ReservationDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".goodsBuyInfo start!");

        return shopMapper.goodsBuyInfo(pDTO);
    }

    @Override
    public ShopDTO getCount(ShopDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getReviewCount Start!");

        ShopDTO rDTO = new ShopDTO();

        rDTO = Optional.ofNullable(shopMapper.getShopInfo(pDTO)).orElseGet(ShopDTO::new);
        rDTO.setReviewCount(Optional.ofNullable(shopMapper.getReviewCount(pDTO)).orElseGet(String::new));
        rDTO.setReserveCount(Optional.ofNullable(shopMapper.getReserveCount(pDTO)).orElseGet(String::new));
        rDTO.setBuyCount(Optional.ofNullable(shopMapper.getBuyCount(pDTO)).orElseGet(String::new));
        rDTO.setReserveStop(Optional.ofNullable(shopMapper.getReserveStop(pDTO)).orElseGet(String::new));

        log.info(this.getClass().getName() + ".getReviewCount Start!");

        return rDTO;
    }


    @Override
    public void deleteBuy(ReservationDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteBuy start!");
        shopMapper.deleteBuy(pDTO);
    }
    @Override
    public void acceptBuy(ReservationDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteBuy start!");
        shopMapper.acceptBuy(pDTO);
    }

    /* 상점정보 조회 코드 */
    @Override
    public ShopDTO getShopInfo(ShopDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getShopInfo Start!");

        ShopDTO rDTO = Optional.ofNullable(shopMapper.getShopInfo(pDTO)).orElseGet(ShopDTO::new);

        log.info(this.getClass().getName() + ".getShopInfo Start!");

        return rDTO;
    }


    /* 상점정보 등록 코드 */
    @Override
    public int insertShopInfo(ShopDTO pDTO) throws Exception {
        int res = 0;

        log.info("인설트 시작");

        res = shopMapper.insertShop(pDTO);

        return res;
    }

    /* 상점 정보 수정 코드 */
    @Override
    public int updateShopInfo(ShopDTO pDTO) throws Exception {
        int res = 0;

        log.info("수정 시작");
        res = shopMapper.updateShop(pDTO);

        return res;
    }

    /* 상점 정보 목록 조회 */
    @Override
    public List<ShopDTO> getShopList(ShopDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getShopList Start!");

        return shopMapper.getShopList(pDTO);
    }
}
