package kopo.poly.controller;

import kopo.poly.dto.CommentDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.ICommentService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    private final ICommentService commentService;

    @ResponseBody
    @PostMapping(value = "/commentInsert")
    public MsgDTO commentInsert(HttpSession session, HttpServletRequest request) {
        log.info("commentInsert Start");
        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));

            log.info("customerId : " + customerId);
            log.info("contents : " + contents);

            CommentDTO pDTO = new CommentDTO();
            pDTO.setRegId(customerId);
            pDTO.setContents(contents);

            commentService.insertComment(pDTO);
            msg = "댓글이 등록되었습니다.";
            res = 1;
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(dto.toString());
            log.info("commentInsert End");
        }
        return dto;
    }


    @ResponseBody
    @PostMapping("/replyInsert")
    public MsgDTO replyInsert(HttpSession session, HttpServletRequest request) {
        log.info("replyInsert Start");
        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));

            log.info("customerId : " + customerId);
            log.info("contents : " + contents);

            CommentDTO pDTO = new CommentDTO();
            pDTO.setRegId(customerId);
            pDTO.setContents(contents);

            commentService.insertReply(pDTO);
            msg = "대댓글이 등록되었습니다.";
            res = 1;
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(dto.toString());
            log.info("replyInsert End");
        }
        return dto;
    }

    @ResponseBody
    @PostMapping(value = "/commentUpdate")
    public MsgDTO postUpdate(HttpSession session, HttpServletRequest request) {

        log.info(this.getClass().getName() + ".commentUpdate Start!");

        String msg = "";
        int res = 1;
        MsgDTO dto = null;

        try {
            String customer_id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String commentNumber = CmmUtil.nvl(request.getParameter("commentNumber"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));

            log.info("customer_id : " + customer_id);
            log.info("commentNumber : " + commentNumber);
            log.info("contents : " + contents);

            CommentDTO pDTO = new CommentDTO();
            pDTO.setRegId(customer_id);
            pDTO.setCommentNumber(commentNumber);
            pDTO.setContents(contents);

            commentService.updateComment(pDTO);

            msg = "수정되었습니다.";
            res = 1;
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(".commentUpdate End!");
        }
        return dto;
    }

    @ResponseBody
    @PostMapping(value = "/commentDelete")
    public MsgDTO commentDelete(HttpSession session, HttpServletRequest request) {

        log.info(this.getClass().getName() + ".commentDelete Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String commentNumber = CmmUtil.nvl(request.getParameter("commentNumber"));

            log.info("customerId : " + customerId);
            log.info("commentNumber : " + commentNumber);

            CommentDTO pDTO = new CommentDTO();
            pDTO.setRegId(customerId);
            pDTO.setCommentNumber(commentNumber);

            commentService.deleteComment(pDTO);

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
            log.info(this.getClass().getName() + ".commentDelete End!");
        }
        return dto;
    }

    @ResponseBody
    @PostMapping("/updateCommentForDeletion")
    public MsgDTO updateCommentForDeletion(HttpSession session, HttpServletRequest request) {

        log.info("updateCommentForDeletion Start");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String commentNumber = CmmUtil.nvl(request.getParameter("commentNumber"));

            log.info("customerId : " + customerId);
            log.info("commentNumber : " + commentNumber);

            CommentDTO pDTO = new CommentDTO();
            pDTO.setRegId(customerId);
            pDTO.setCommentNumber(commentNumber);

            commentService.updateCommentForDeletion(pDTO);

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
            log.info("updateCommentForDeletion End!");
        }
        return dto;
    }
}