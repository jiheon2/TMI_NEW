package kopo.poly.service.impl;

import kopo.poly.dto.ChatDTO;
import kopo.poly.persistance.mongodb.IMongoMapper;
import kopo.poly.service.IMongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MongoService implements IMongoService {
    private final IMongoMapper mongoMapper;

    @Override
    public int insertChat(ChatDTO pDTO) throws Exception {

        log.info("insertChat start");

        String colNm = "chat";

        int res = mongoMapper.insertData(pDTO, colNm);

        log.info("insertChat end");

        return res;
    }
}
