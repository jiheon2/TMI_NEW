package kopo.poly.service;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.PaymentDTO;
import kopo.poly.dto.WishListDTO;

import java.util.List;

public interface IWishlistService {
    int addWishlist(WishListDTO pDTO) throws Exception; // 찜목록에 담기

    List<WishListDTO> getWishList(WishListDTO pDTO) throws Exception; // 찜 목록 조회
    int deleteWish(WishListDTO pDTO) throws Exception; // 찜 삭제

}
