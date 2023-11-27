package kopo.poly.persistance.mapper;


import kopo.poly.dto.ReservationDTO;
import kopo.poly.dto.ShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IShopMapper {
    List<ReservationDTO> goodsBuyInfo(ReservationDTO pDTO) throws Exception; //예약 정보 확인
    void deleteBuy(ReservationDTO pDTO) throws Exception;
    void acceptBuy(ReservationDTO pDTO) throws Exception;
    int insertShop(ShopDTO pDTO) throws Exception; // 상점 정보 입력
    ShopDTO getShopInfo(ShopDTO pDTO) throws Exception; // 상점 정보 조회
    int updateShop(ShopDTO pDTO) throws Exception; // 상점 정보 수정
    String getReviewCount(ShopDTO pDTO) throws Exception; // 리뷰 개수 조회
    String getReserveCount(ShopDTO pDTO) throws Exception; // 예약 개수 조회
    String getBuyCount(ShopDTO pDTO) throws Exception; // 당일 구매 개수 조회
    String getReserveStop(ShopDTO pDTO) throws Exception; // 예약 개수 조회
    List<ShopDTO> getDateCount(ShopDTO pDTO) throws  Exception; // ?
    List<ShopDTO> getShopList(ShopDTO pDTO) throws Exception; // 상점 리스트 조회
}
