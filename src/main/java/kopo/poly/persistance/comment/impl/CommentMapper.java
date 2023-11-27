package kopo.poly.persistance.comment.impl;

import com.mongodb.client.MongoCollection;
import kopo.poly.dto.CommentDTO;
import kopo.poly.persistance.comment.AbstractMongoDBComon;
import kopo.poly.persistance.comment.ICommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;



@Slf4j
@RequiredArgsConstructor
@Component
public class CommentMapper extends AbstractMongoDBComon implements ICommentMapper {
    private final MongoTemplate mongodb;

    @Override
    public int insertComment(CommentDTO pDTO, String colNm) throws Exception {

        log.info("insertComment start");

        int res = 0;

        super.createCollection(mongodb, colNm);

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        return 0;
    }
}
