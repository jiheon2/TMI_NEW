package kopo.poly.persistance.mapper;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.TraderDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ITraderMapper {
    int insertTrader(TraderDTO pDTO) throws Exception; // 상인 회원가입

    int updateTraderInfo(TraderDTO pDTO) throws Exception; // 상인 정보 변경

    int updateTraderPw(TraderDTO pDTO) throws Exception; // 비밀번호 변경

    TraderDTO getLogin(TraderDTO pDTO) throws Exception; // 로그인 정보 확인

    TraderDTO getTraderInfo(TraderDTO pDTO) throws Exception; // 상인정보 조회

    TraderDTO getBusinessNumberExists(TraderDTO pDTO) throws Exception; // 사업자등록번호 중복확인

    TraderDTO getTraderIdExists(TraderDTO pDTO) throws Exception; // ID 중복확인
    TraderDTO getEmailExists(TraderDTO pDTO) throws Exception; // 이메일 확인
    TraderDTO getTraderId(TraderDTO pDTO) throws Exception; // 아이디 보여주기
    TraderDTO getTraderPw(TraderDTO pDTO) throws Exception; // 비밀번호 보여주기
}
