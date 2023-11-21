package kopo.poly.persistance.mapper;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.WishListDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IWishlistMapper {
    int addWishlist(WishListDTO pDTO) throws Exception; // 찜목록에 담기
}
