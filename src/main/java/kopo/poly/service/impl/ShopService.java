package kopo.poly.service.impl;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.ProductDTO;
import kopo.poly.dto.ShopDTO;
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
