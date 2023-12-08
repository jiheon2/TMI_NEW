package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import kopo.poly.dto.ChatDTO;
import kopo.poly.persistance.mongodb.IMongoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class MongoMapper implements IMongoMapper {
    private final MongoTemplate mongodb;
    @Override
    public int insertData(ChatDTO pDTO, String colNm) throws Exception {
        log.info("insertData start");

        int res = 0;

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        res = 1;

        log.info("insertData end");

        return res;
    }
}
