package kopo.poly.service;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.GoodsDTO;
import kopo.poly.dto.PaymentDTO;

import java.util.List;


public interface IBasketService {
    int addBasket(BasketDTO pDTO) throws Exception; // 장바구니에 담기
    List<PaymentDTO> getPayment(PaymentDTO pDTO) throws Exception; // 결제목록 보기
    List<BasketDTO> getBasketList(BasketDTO pDTO) throws Exception; // 장바구니 목록 조회
    int insertPayment(PaymentDTO pDTO) throws Exception; // 결제목록 담기
    PaymentDTO paymentInfo(PaymentDTO pDTO) throws Exception; //결제내역 상세보기

    int deleteBuy(BasketDTO pDTO) throws Exception; // 장바구니 삭제

}
