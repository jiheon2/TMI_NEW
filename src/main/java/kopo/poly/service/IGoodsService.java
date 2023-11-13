package kopo.poly.service;

import kopo.poly.dto.GoodsDTO;

import java.util.List;

public interface IGoodsService {
    List<GoodsDTO> getGoodsList(GoodsDTO pDTO) throws Exception; // 상품 목록 조회
    GoodsDTO getGoodsInfo(GoodsDTO pDTO) throws Exception; // 상품 정보 조회
    int updateGoods(GoodsDTO pDTO) throws Exception; // 상품 정보 수정
    int insertGoods(GoodsDTO pDTO) throws Exception; // 상품 정보 등록
    void goodsInfoDelete(GoodsDTO pDTO) throws Exception; // 등록 상품 삭제
}
