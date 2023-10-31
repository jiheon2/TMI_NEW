package kopo.poly.service;

import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.ProductDTO;
import kopo.poly.dto.ShopDTO;

import java.util.List;

public interface IShopService {
    List<ProductDTO> getGoodsList(ProductDTO pDTO) throws Exception;

    ProductDTO getGoodsInfo(ProductDTO pDTO) throws Exception;
    int changeGoods(ProductDTO pDTO) throws Exception;


    ShopDTO getShopInfo(ShopDTO pDTO) throws Exception;
    void goodsMsgDelete(ProductDTO pDTO) throws Exception;

    int changeShop(ShopDTO pDTO) throws Exception;
}
