package kopo.poly.service.impl;

import kopo.poly.dto.PostDTO;
import kopo.poly.persistance.mapper.IPostMapper;
import kopo.poly.service.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService implements IPostService {
    private final IPostMapper noticeMapper;
    @Override
    public List<PostDTO> getPostList() throws Exception {

        log.info(this.getClass().getName() + ".getPostList start!");

        return noticeMapper.getPostList();
    }

    @Transactional
    @Override
    public PostDTO getPostInfo(PostDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getnoticeInfo start!");

            log.info("Update ReadCNT");
            noticeMapper.updatePostReadCnt(pDTO);

        return noticeMapper.getPostInfo(pDTO);
    }

    @Transactional
    @Override
    public void insertPostInfo(PostDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".InsertPostInfo start!");
        noticeMapper.insertPostInfo(pDTO);
    }

    @Transactional
    @Override
    public void updatePostInfo(PostDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".updatePostInfo start!");
        noticeMapper.updatePostInfo(pDTO);
    }

    @Override
    public void deletePostInfo(PostDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deletePostInfo start!");
        noticeMapper.deletePostInfo(pDTO);
    }

    @Override
    public int getPostNumber() throws Exception {
        return 0;
    }

    @Override
    public void updatePostReadCnt(PostDTO pDTO) throws Exception {

    }
}
