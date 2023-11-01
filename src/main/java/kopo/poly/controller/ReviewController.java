package kopo.poly.controller;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.ProductDTO;
import kopo.poly.dto.ReviewDTO;
import kopo.poly.service.IReviewService;
import kopo.poly.service.IShopService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final IReviewService reviewService;
    private final IShopService shopService;

    @GetMapping(value = "/trader/reviewMng")
    public String getReviewList(HttpSession session, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".getReviewList start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
//        String seq = CmmUtil.nvl(request.getParameter("seq"));

        log.info("SS_ID : " + id);

        ReviewDTO pDTO = new ReviewDTO();

//        ProductDTO gDTO = new ProductDTO();

        pDTO.setTraderId(id);
//        gDTO.setPid(id);
//        gDTO.setSeq(seq);

        List<ReviewDTO> rList = Optional.ofNullable(reviewService.getReviewList(pDTO)).orElseGet(ArrayList::new);

//        ProductDTO rDTO = Optional.ofNullable(shopService.getGoodsInfo(gDTO)).orElseGet(ProductDTO::new);
        model.addAttribute("rList", rList);
//        model.addAttribute("rDTO", rDTO);


        log.info(this.getClass().getName() + ".getReviewList End!");

        return "/trader/reviewMng";
    }

    @ResponseBody
    @PostMapping(value = "/trader/deleteReview")
    public MsgDTO deleteReview(HttpSession session, @RequestBody List<String> checkboxes) {
        log.info(this.getClass().getName() + ".reviewDelete Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
//            ArrayList<String> checkboxes = CmmUtil.nvl(request.getParameter("checkboxes"));

            log.info("id : " + id);
            log.info("checkboxes : " + checkboxes);

            ReviewDTO pDTO = new ReviewDTO();
            for(String seq : checkboxes) {
                pDTO.setSeq(seq);
                pDTO.setTraderId(id);
                reviewService.deleteReview(pDTO);
            }

            msg = "삭제되었습니다.";
            res = 1;
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);
            log.info(this.getClass().getName() + ".reviewDelete End!");
        }

        return dto;
    }
}
