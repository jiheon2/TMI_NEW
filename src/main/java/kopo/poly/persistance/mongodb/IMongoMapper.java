package kopo.poly.persistance.mongodb;

import kopo.poly.dto.ChatDTO;

public interface IMongoMapper {
    int insertData(ChatDTO pDTO, String colNm) throws Exception;
}
