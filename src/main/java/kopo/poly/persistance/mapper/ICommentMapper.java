package kopo.poly.persistance.mapper;

import kopo.poly.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICommentMapper {

    /* 댓글 작성 */
    void insertComment(CommentDTO pDTO);

    /* 대댓글 작성 */
    void insertReply(CommentDTO pDTO);

    /* 댓글 수정 */
    void updateComment(CommentDTO pDTO);

    /* 댓글 삭제 */
    void deleteComment(CommentDTO pDTO);

    /* 대댓글 삭제 */
    void deleteReply(CommentDTO pDTO);

    /* 대댓글 보유 댓글 삭제시 변경 */
    void updateCommentForDeletion(CommentDTO pDTO);

    /* 댓글 목록 조회 */
    List<CommentDTO> getCommentList(CommentDTO pDTO);

}
