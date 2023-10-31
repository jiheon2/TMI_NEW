package kopo.poly.service;

import kopo.poly.dto.ReviewDTO;

import java.util.List;

public interface IReviewService {
    List<ReviewDTO> getReviewList(ReviewDTO pDTO) throws Exception;
    void deleteReview(ReviewDTO pDTO) throws Exception;
}
