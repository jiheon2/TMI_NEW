package kopo.poly.persistance.mapper;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.GoodsDTO;
import kopo.poly.dto.PaymentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBasketMapper {
    int addBasket(BasketDTO pDTO) throws Exception; // 장바구니에 담기
    List<BasketDTO> getBasketList(BasketDTO pDTO) throws Exception; // 장바구니 목록 조회
    int insertPayment(PaymentDTO pDTO) throws Exception; // 결제목록 담기
    int deleteBuy(BasketDTO pDTO) throws Exception; // 장바구니 삭제
}
