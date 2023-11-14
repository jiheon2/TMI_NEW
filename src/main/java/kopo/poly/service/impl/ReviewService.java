package kopo.poly.service.impl;

import kopo.poly.dto.ReviewDTO;
import kopo.poly.persistance.mapper.IReviewMapper;
import kopo.poly.service.IReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final IReviewMapper reviewMapper;

    /* 리뷰 목록 조회 코드 */
    @Override
    public List<ReviewDTO> getReviewList(ReviewDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getReviewList Start!");

        log.info("값은 : " + reviewMapper.getReviewList(pDTO).toString());

        return reviewMapper.getReviewList(pDTO);
    }

    /* 리뷰 삭제 코드 */
    @Override
    public void deleteReview(ReviewDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".deleteReview Start!");

        reviewMapper.deleteReview(pDTO);
    }
}
