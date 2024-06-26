package kopo.poly.service.impl;

import kopo.poly.dto.BasketDTO;
import kopo.poly.dto.WishListDTO;
import kopo.poly.persistance.mapper.IBasketMapper;
import kopo.poly.persistance.mapper.IWishlistMapper;
import kopo.poly.service.IWishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WishlistService implements IWishlistService {
    private final IWishlistMapper wishlistMapper;
    @Override
    public int addWishlist(WishListDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".addWishlist Start!");

        int res = 0;
        try{
            //회원가입 성공시 1, 에러 0


            int success = wishlistMapper.addWishlist(pDTO);

            if(success > 0) {
                res = 1;
            } else {
                res = 0;
            }

            log.info(this.getClass().getName() + ".addWishlist Start!");
        }catch (Exception e) {
            int success = wishlistMapper.deleteWish(pDTO);
            if(success > 0) {
                res = 2;
            } else {
                res = 0;
            }

        }

        return res;
    }
    @Override
    public List<WishListDTO> getWishList(WishListDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getWishList start!");

        return wishlistMapper.getWishList(pDTO);
    }
    @Override
    public int deleteWish(WishListDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteWish Start!");

        //회원가입 성공시 1, 에러 0
        int res = 0;

        int success = wishlistMapper.deleteWish(pDTO);

        if(success > 0) {
            res = 1;
        } else {
            res = 0;
        }

        log.info(this.getClass().getName() + ".deleteWish Start!");
        return res;
    }
}
