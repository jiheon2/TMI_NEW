package kopo.poly.service.impl;

import kopo.poly.dto.GoodsDTO;
import kopo.poly.dto.MarketDTO;
import kopo.poly.dto.ReservationDTO;
import kopo.poly.dto.ShopDTO;
import kopo.poly.persistance.mapper.IReservationMapper;
import kopo.poly.service.IReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService implements IReservationService {
    private final IReservationMapper reservationMapper;

    /* 예약 목록 조회 */
    @Override
    public List<ReservationDTO> getReservationList() throws Exception {

        log.info(this.getClass().getName() + ".getReservationList Start!");

        return reservationMapper.getReservationList();
    }

    /* 예약 정보 등록 */
    @Override
    public void insertReservationInfo(ReservationDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertReservationInfo Start!");

        reservationMapper.insertReservationInfo(pDTO);
    }

    /* 인기 상품 조회 */
    @Override
    public List<GoodsDTO> getPopularGoods(String market) throws Exception {

        log.info(this.getClass().getName() + ".getPopularGoods Start!");

        return reservationMapper.getPopularGoods(market);
    }
    /* 인기 상점 조회 */
    @Override
    public List<ShopDTO> getPopularShop() throws Exception {

        log.info(this.getClass().getName() + ".getPopularShop Start!");

        return reservationMapper.getPopularShop();
    }
    /* 인기 시장 조회 */
    @Override
    public List<MarketDTO> getPopularMarket() throws Exception {

        log.info(this.getClass().getName() + ".getPopularMarket Start!");

        return reservationMapper.getPopularMarket();
    }
    /* 예약 상세 정보 조회 */
    @Override
    public ReservationDTO getReservationInfo(ReservationDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getReservationInfo Start!");

        return reservationMapper.getReservationInfo(pDTO);
    }

    /* 예약 정보 수정 */
    @Override
    public void updateReservationInfo(ReservationDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".updateReservationInfo Start!");

        reservationMapper.updateReservationInfo(pDTO);
    }

    /* 예약 상태 수정 */
    @Override
    public void updateReservationState(ReservationDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".updateReservationState Start!");

        reservationMapper.updateReservationState(pDTO);
    }

    /* 예약 정보 삭제 */
    @Override
    public void deleteReservationInfo(ReservationDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".deleteReservationInfo Start!");

        reservationMapper.deleteReservationInfo(pDTO);
    }

    /* 당일 예약 목록 조회 */
    @Override
    public List<ReservationDTO> getTodayReservationList(ReservationDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getTodayReservationList");

        return reservationMapper.getTodayReservationList(pDTO);
    }
}
