package kopo.poly.persistance.mapper;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.WishListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IWishlistMapper {
    int addWishlist(WishListDTO pDTO) throws Exception; // 찜목록에 담기
    List<WishListDTO> getWishList(WishListDTO pDTO) throws Exception; // 찜 목록 조회

    int deleteWish(WishListDTO pDTO) throws Exception; // 찜 삭제
}
