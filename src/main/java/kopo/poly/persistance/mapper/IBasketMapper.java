package kopo.poly.persistance.mapper;

import kopo.poly.dto.BasketDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBasketMapper {
    int addBasket(BasketDTO pDTO) throws Exception; // 장바구니에 담기
}
