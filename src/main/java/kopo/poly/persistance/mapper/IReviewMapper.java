package kopo.poly.persistance.mapper;

import kopo.poly.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IReviewMapper {

    List<ReviewDTO> getReviewList(ReviewDTO pDTO) throws Exception;

    void deleteReview(ReviewDTO pDTO) throws Exception;
}

