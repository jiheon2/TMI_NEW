package kopo.poly.service;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.GoodsDTO;

import java.util.List;


public interface IBasketService {
    int addBasket(BasketDTO pDTO) throws Exception; // 장바구니에 담기
    List<BasketDTO> getBasketList(BasketDTO pDTO) throws Exception; // 장바구니 목록 조회
}
