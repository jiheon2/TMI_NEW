package kopo.poly.service;

import kopo.poly.dto.CustomerDTO;

public interface ICustomerService {
    int insertCustomer(CustomerDTO pDTO) throws Exception; // 소비자 회원가입

    CustomerDTO getLogin(CustomerDTO pDTO) throws Exception; // 로그인 정보 확인

    CustomerDTO getCustomerIdExists(CustomerDTO pDTO) throws Exception; // 소비자 ID 중복확인

    CustomerDTO getCustomerInfo(CustomerDTO pDTO) throws Exception; // 소비자 정보 조회
    int updateCustomerInfo(CustomerDTO pDTO) throws Exception; // 소비자 정보 수정
    int updateCustomerPw(CustomerDTO pDTO) throws Exception; // 소비자 비밀번호 변경
    int pointReward(CustomerDTO pDTO) throws Exception; // 로그인 포인트 업데이트
    void resetRewardAndRotate() throws Exception; // 포인트 획득 여부 초기화
    CustomerDTO searchEmail(CustomerDTO pDTO) throws Exception; // 이메일 찾기
    CustomerDTO searchCustomerId(CustomerDTO pDTO) throws Exception; // 아이디 변경
    CustomerDTO searchCustomerPw(CustomerDTO pDTO) throws Exception; // 비밀번호 변경
    CustomerDTO customerInfoForReservation(CustomerDTO pDTO) throws Exception;
    void rotate(CustomerDTO pDTO) throws Exception;
}
