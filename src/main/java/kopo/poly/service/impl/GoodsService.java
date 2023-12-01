package kopo.poly.service.impl;

import kopo.poly.dto.GoodsDTO;
import kopo.poly.dto.ReservationDTO;
import kopo.poly.persistance.mapper.IGoodsMapper;
import kopo.poly.service.IGoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsService implements IGoodsService {
    
    private final IGoodsMapper goodsMapper;

    /* 상품 목록 조회 */
    @Override
    public List<GoodsDTO> getGoodsList(GoodsDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getGoodsList start!");

        return goodsMapper.getGoodsList(pDTO);
    }

    /* 상품 정보 조회 */
    @Override
    public GoodsDTO getGoodsInfo(GoodsDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getGoodsInfo Start!");

        GoodsDTO rDTO = Optional.ofNullable(goodsMapper.getGoodsInfo(pDTO)).orElseGet(GoodsDTO::new);

        log.info(this.getClass().getName() + ".getGoodsInfo Start!");

        return rDTO;
    }

    /* 상품 정보 수정 */
    @Override
    public int updateGoods(GoodsDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateGoods Start!");

        int res = 0;

        res = goodsMapper.updateGoods(pDTO);

        return res;
    }

    /* 상품 정보 등록 */
    @Override
    public int insertGoods(GoodsDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertGoods Start!");

        int res = 0;

        res = goodsMapper.insertGoods(pDTO);

        return res;
    }

    /* 상품 정보 삭제 */
    @Override
    public void goodsInfoDelete(GoodsDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".goodsMsgDelete start!");
        goodsMapper.goodsInfoDelete(pDTO);
    }
}
