package kopo.poly.persistance.mapper;

import kopo.poly.dto.GoodsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IGoodsMapper {
    List<GoodsDTO> getGoodsList(GoodsDTO pDTO) throws Exception; // 상품 목록 조회
    GoodsDTO getGoodsInfo(GoodsDTO pDTO) throws Exception; // 상품 정보 조회
    int updateGoods(GoodsDTO pDTO) throws Exception; // 상품 정보 수정
    int insertGoods(GoodsDTO pDTO) throws Exception; // 상품 등록
    void goodsInfoDelete(GoodsDTO pDTO) throws Exception; // 등록 상품 삭제
}
