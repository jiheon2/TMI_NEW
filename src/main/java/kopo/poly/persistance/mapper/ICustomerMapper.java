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
    void resetReward() throws Exception; // 포인트 획득 여부 초기화
}
