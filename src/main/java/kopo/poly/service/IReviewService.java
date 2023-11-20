package kopo.poly.service;

import kopo.poly.dto.PostDTO;
import kopo.poly.dto.ReviewDTO;

import java.util.List;

public interface IReviewService {
    List<ReviewDTO> getReviewList(ReviewDTO pDTO) throws Exception; // 리뷰 목록 조회
    List<ReviewDTO> oneReviewList(ReviewDTO pDTO) throws Exception; // 상품 리뷰 목록 조회

    List<ReviewDTO> getScore(ReviewDTO pDTO) throws Exception; // 리뷰 목록 조회

    void deleteReview(ReviewDTO pDTO) throws Exception; // 리뷰 삭제
    void insertReview(ReviewDTO pDTO) throws Exception; // 리뷰 등록

}
