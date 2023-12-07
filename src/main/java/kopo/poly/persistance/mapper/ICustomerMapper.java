package kopo.poly.persistance.mapper;

import kopo.poly.dto.CustomerDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICustomerMapper {

    int insertCustomer(CustomerDTO pDTO) throws Exception; // 소비자 회원가입

    CustomerDTO getLogin(CustomerDTO pDTO) throws Exception; // 로그인 정보 확인

    CustomerDTO getCustomerIdExists(CustomerDTO pDTO) throws Exception; // 소비자 ID 중복확인

    CustomerDTO getCustomerInfo(CustomerDTO pDTO) throws Exception; // 소비자 정보 조회
    int updateCustomerInfo(CustomerDTO pDTO) throws Exception; // 소비자 정보 수정
    int updateCustomerPw(CustomerDTO pDTO) throws Exception; // 소비자 비밀번호 변경
    int pointReward(CustomerDTO pDTO) throws Exception; // 로그인 포인트 업데이트
    void resetRewardAndRotate() throws Exception; // 포인트 획득 여부, 룰렛 작동여부 초기화
    CustomerDTO getEmailExists(CustomerDTO pDTO) throws Exception; // 이메일 확인
    CustomerDTO getCustomerId(CustomerDTO pDTO) throws Exception; // 아이디 보여주기
    CustomerDTO getCustomerPw(CustomerDTO pDTO) throws Exception; // 비밀번호 보여주기
    void rotate(CustomerDTO pDTO) throws Exception; // 룰렛 한번만 돌리기
    CustomerDTO customerInfoForReservation(CustomerDTO pDTO) throws Exception;
}
