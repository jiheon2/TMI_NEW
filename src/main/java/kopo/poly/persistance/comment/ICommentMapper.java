package kopo.poly.persistance.comment;

import kopo.poly.dto.CommentDTO;

public interface ICommentMapper {
    /**
     *  댓글 저장
     * @param pDTO 저장될 정보
     * @param colNm 저장할 컬렉션 이름
     * @return 저장 결과
     */
    int insertComment(CommentDTO pDTO, String colNm) throws Exception;
}
