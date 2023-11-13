package kopo.poly.service.impl;

import kopo.poly.dto.GoodsDTO;
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

    @Override
    public int updateAndInsertGoodsInfo(GoodsDTO pDTO) throws Exception {
        int result = 0;
        int res = 0;
        try {
            log.info("수정 시작");
            result = goodsMapper.updateGoodsInfo(pDTO);

        } catch (Exception e) {
            if(result == 0) {
                log.info("인설트 시작");
                result = goodsMapper.insertGoodsInfo(pDTO);
            }

        } finally {
            if (result > 0) {
                res = 1;
            }
        }

        return res;
    }

    @Override
    public void goodsInfoDelete(GoodsDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".goodsMsgDelete start!");
        goodsMapper.goodsInfoDelete(pDTO);
    }
}
