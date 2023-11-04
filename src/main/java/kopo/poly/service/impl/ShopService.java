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
    public List<ReserveDTO> goodsBuyInfo(ReserveDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".goodsBuyInfo start!");

        return shopMapper.goodsBuyInfo(pDTO);
    }
    @Override
    public void deleteBuy(ReserveDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteBuy start!");
        shopMapper.deleteBuy(pDTO);
    }
    @Override
    public void acceptBuy(ReserveDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteBuy start!");
        shopMapper.acceptBuy(pDTO);
    }
    @Override
    public List<ProductDTO> getGoodsList(ProductDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getGoodsList start!");

        return shopMapper.getGoodsList(pDTO);
    }
    @Override
    public ProductDTO getGoodsInfo(ProductDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getGoodsInfo Start!");


        ProductDTO rDTO = Optional.ofNullable(shopMapper.getGoodsInfo(pDTO)).orElseGet(ProductDTO::new);

        log.info(this.getClass().getName() + ".getGoodsInfo Start!");

        return rDTO;
    }
    @Override
    public int changeGoods(ProductDTO pDTO) throws Exception {
        int result = 0;
        int res = 0;
        log.info("수정 시작");
        result = shopMapper.changeGoods(pDTO);
        if(result == 0) {
            log.info("인설트 시작");
            result = shopMapper.insertGoods(pDTO);
        }

        if (result > 0) {
            res = 1;
        }
        return res;
    }
    @Override
    public void goodsMsgDelete(ProductDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".goodsMsgDelete start!");
        shopMapper.goodsMsgDelete(pDTO);
    }
    @Override
    public ShopDTO getShopInfo(ShopDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getShopInfo Start!");


        ShopDTO rDTO = Optional.ofNullable(shopMapper.getShopInfo(pDTO)).orElseGet(ShopDTO::new);

        log.info(this.getClass().getName() + ".getShopInfo Start!");

        return rDTO;
    }

    @Override
    public int changeShop(ShopDTO pDTO) throws Exception {
        int result = 0;
        int res = 0;
            log.info("수정 시작");
            result = shopMapper.changeShop(pDTO);
            if(result == 0) {
                log.info("인설트 시작");
                result = shopMapper.insertShop(pDTO);
            }

        if (result > 0) {
            res = 1;
        }
        return res;
    }

}
