package kopo.poly.persistance.mapper;

import kopo.poly.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IReviewMapper {

    List<ReviewDTO> getReviewList(ReviewDTO pDTO) throws Exception; // 리뷰 목록 조회

    void deleteReview(ReviewDTO pDTO) throws Exception; // 리뷰 삭제
}

