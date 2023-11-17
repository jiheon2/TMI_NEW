package kopo.poly.service;

import kopo.poly.dto.ReservationDTO;

import java.util.List;

public interface IReservationService {
    List<ReservationDTO> getReservationList() throws Exception; // 예약 목록 조회 쿼리
    List<ReservationDTO> getTodayReservationList(ReservationDTO pDTO) throws Exception; // 당일 예약 목록 조회 쿼리

    void insertReservationInfo(ReservationDTO pDTO) throws Exception; // 예약 일정 등록 쿼리
    ReservationDTO getReservationInfo(ReservationDTO pDTO) throws Exception; // 예약 상세정보 조회 쿼리
    void updateReservationInfo(ReservationDTO pDTO) throws Exception; // 예약 수정 쿼리
    void updateReservationState(ReservationDTO pDTO) throws Exception; // 예약 상태 변경 쿼리
    void deleteReservationInfo(ReservationDTO pDTO) throws Exception; // 예약 정보 삭제 쿼리
}
