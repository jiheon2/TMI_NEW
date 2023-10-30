package kopo.poly.persistance.mapper;


import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.ProductDTO;
import kopo.poly.dto.ShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IShopMapper {
    List<ProductDTO> getGoodsList(ProductDTO pDTO) throws Exception;
    ProductDTO getGoodsInfo(ProductDTO pDTO) throws Exception;


    int insertShop(ShopDTO pDTO) throws Exception;
    ShopDTO getShopInfo(ShopDTO pDTO) throws Exception;
    int changeShop(ShopDTO pDTO) throws Exception;
}
