package kopo.poly.service;

import kopo.poly.dto.WishListDTO;

public interface IWishlistService {
    int addWishlist(WishListDTO pDTO) throws Exception; // 찜목록에 담기
}
