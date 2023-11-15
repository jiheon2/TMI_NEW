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
    private final IPostMapper postMapper;
    @Override
    public List<PostDTO> getPostList() throws Exception {

        log.info(this.getClass().getName() + ".getPostList start!");

        return postMapper.getPostList();
    }

    @Transactional
    @Override
    public PostDTO getPostInfo(PostDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getPostInfo start!");

            log.info("Update ReadCNT");
            postMapper.updatePostReadCnt(pDTO);

        return postMapper.getPostInfo(pDTO);
    }

    @Transactional
    @Override
    public void insertPostInfo(PostDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".InsertPostInfo start!");
        postMapper.insertPostInfo(pDTO);
    }

    @Transactional
    @Override
    public void updatePostInfo(PostDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".updatePostInfo start!");
        postMapper.updatePostInfo(pDTO);
    }

    @Override
    public void deletePostInfo(PostDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deletePostInfo start!");
        postMapper.deletePostInfo(pDTO);
    }

    @Override
    public int getPostNumber() throws Exception {
        return 0;
    }

    @Override
    public void updatePostReadCnt(PostDTO pDTO) throws Exception {

    }
}
