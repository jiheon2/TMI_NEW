package kopo.poly.persistance.mapper;


import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.ProductDTO;
import kopo.poly.dto.ReserveDTO;
import kopo.poly.dto.ShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IShopMapper {
    List<ReserveDTO> goodsBuyInfo(ReserveDTO pDTO) throws Exception;
    void deleteBuy(ReserveDTO pDTO) throws Exception;
    void acceptBuy(ReserveDTO pDTO) throws Exception;
    List<ProductDTO> getGoodsList(ProductDTO pDTO) throws Exception;
    ProductDTO getGoodsInfo(ProductDTO pDTO) throws Exception;
    int changeGoods(ProductDTO pDTO) throws Exception;
    int insertGoods(ProductDTO pDTO) throws Exception;

    int insertShop(ShopDTO pDTO) throws Exception;
    ShopDTO getShopInfo(ShopDTO pDTO) throws Exception;
    void goodsMsgDelete(ProductDTO pDTO) throws Exception;
    int changeShop(ShopDTO pDTO) throws Exception;
}
