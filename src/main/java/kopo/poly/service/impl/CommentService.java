package kopo.poly.service.impl;

import kopo.poly.dto.CommentDTO;
import kopo.poly.persistance.mapper.ICommentMapper;
import kopo.poly.service.ICommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final ICommentMapper commentMapper;

    @Transactional
    @Override
    public void insertComment(CommentDTO pDTO) {
        log.info("insertComment Start");

        commentMapper.insertComment(pDTO);
    }

    @Transactional
    @Override
    public void insertReply(CommentDTO pDTO) {
        log.info("insertReply Start");

        commentMapper.insertReply(pDTO);
    }

    @Transactional
    @Override
    public void updateComment(CommentDTO pDTO) {
        log.info("updateComment Start");

        commentMapper.updateComment(pDTO);
    }

    @Transactional
    @Override
    public void deleteComment(CommentDTO pDTO) {
        log.info("deleteComment Start");

        commentMapper.deleteComment(pDTO);
    }

    @Transactional
    @Override
    public void deleteReply(CommentDTO pDTO) {
        log.info("deleteReply Start");

        commentMapper.deleteReply(pDTO);
    }

    @Transactional
    @Override
    public void updateCommentForDeletion(CommentDTO pDTO) {
        log.info("updateCommentForDeletion");

        commentMapper.updateCommentForDeletion(pDTO);
    }

    @Override
    public List<CommentDTO> getCommentList(CommentDTO pDTO) {
        log.info("getCommentList Start");

        return commentMapper.getCommentList(pDTO);
    }
}
