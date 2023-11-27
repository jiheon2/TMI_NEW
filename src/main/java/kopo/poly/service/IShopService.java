package kopo.poly.service;

import kopo.poly.dto.ReservationDTO;
import kopo.poly.dto.ShopDTO;

import java.util.List;

public interface IShopService {
    int insertShopInfo(ShopDTO pDTO) throws Exception; // 상점 정보 입력
    int updateShopInfo(ShopDTO pDTO) throws Exception; // 상점 정보 수정
    ShopDTO getShopInfo(ShopDTO pDTO) throws Exception; // 상점 정보 조회
    List<ReservationDTO> goodsBuyInfo(ReservationDTO pDTO) throws Exception; //예약 정보 확인
    ShopDTO getCount(ShopDTO pDTO) throws Exception; // 개수 조회(리뷰, 예약, 당일구매)
    List<ShopDTO> getShopList(ShopDTO pDTO) throws  Exception; // 상점 리스트 조회

    void deleteBuy(ReservationDTO pDTO) throws Exception;
    void acceptBuy(ReservationDTO pDTO) throws Exception;
}
