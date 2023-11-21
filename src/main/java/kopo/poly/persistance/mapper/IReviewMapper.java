package kopo.poly.persistance.mapper;

import kopo.poly.dto.PostDTO;
import kopo.poly.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IReviewMapper {

    List<ReviewDTO> getReviewList(ReviewDTO pDTO) throws Exception; // 리뷰 목록 조회
    List<ReviewDTO>  oneReviewList(ReviewDTO pDTO) throws Exception; // 상품 리뷰 목록 조회

    void insertReview(ReviewDTO pDTO) throws Exception; // 리뷰 등록
    void deleteReview(ReviewDTO pDTO) throws Exception; // 리뷰 삭제
    List<ReviewDTO> getScore(ReviewDTO pDTO) throws Exception; // 별점 개수
}

