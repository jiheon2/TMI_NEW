package kopo.poly.controller;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.IFileService;
import kopo.poly.service.INoticeService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Slf4j
@RequestMapping(value = "/notice")
//특정 메소드와 매핑하기 위해 사용, 구형이다
@RequiredArgsConstructor
@Controller
public class NoticeController {
    private final INoticeService noticeService;
    private final IFileService fileService;
//GET 방식은 데이터 조회, POST 방식에서 새로운 데이터 추가.
    @GetMapping(value = "/noticeList")
    public String noticeList(ModelMap model)
            throws Exception {
        log.info(this.getClass().getName() + ".noticeList Start!");

        List<NoticeDTO> rList = noticeService.getNoticeList();
        if (rList == null) rList = new ArrayList<>();

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".noticeList End!");
        return "/notice/noticeList";
    }
    @GetMapping(value = "/noticeReg")
    public String NoticeReg() {
        log.info(this.getClass().getName() + ".noticeReg Start!");
        log.info(this.getClass().getName() + ".noticeReg End!");

        return "/notice/noticeReg";
    }

    @ResponseBody
    @PostMapping(value = "/noticeInsert")
    public MsgDTO noticeInsert(HttpServletRequest request, HttpSession session, @RequestParam(value = "fileUpload") MultipartFile mf) {
        log.info(this.getClass().getName() + ".noticeInsert Start!");
        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String user_id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String title = CmmUtil.nvl(request.getParameter("title"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));
            String type = "Notice";
            String noticeYn = "N";
            log.info("session user_id : " + user_id);
            log.info("title : " + title);
            log.info("contents : " + contents);

            if (user_id.equals("master")) {
                noticeYn = "Y";
            }

            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setSender(user_id);
            pDTO.setTitle(title);
            pDTO.setContents(contents);
            pDTO.setNoticeYn(noticeYn);
            if(!mf.isEmpty()) {
                String image = mf.getOriginalFilename();
                String fileName = image;
                fileService.upload(fileName,type + "/" + pDTO.getSender(), mf);
                pDTO.setImage(fileService.getFileURL("Notice" + "/" + pDTO.getSender(), fileName));
                log.info(pDTO.getImage());
            }
            noticeService.insertNoticeInfo(pDTO);
            msg = "등록되었습니다.";
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
            log.info(this.getClass().getName() + ".noticeInsert End!");
        }

        return dto;
    }

    @GetMapping(value = "/noticeInfo")
    public String noticeInfo(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".noticeInfo Start!");
        String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));

        log.info("nSeq : " + nSeq);

        NoticeDTO pDTO = new NoticeDTO();
        pDTO.setSeq(nSeq);

        NoticeDTO rDTO = Optional.ofNullable(noticeService.getNoticeInfo(pDTO))
                .orElseGet(NoticeDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".noticeInfo End!");

        return "/notice/noticeInfo";
    }

    @GetMapping(value = "/noticeEditInfo")
    public String noticeEditInfo(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".noticeEditInfo Start!");

        String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));

        log.info("nSeq : " + nSeq);

        NoticeDTO pDTO = new NoticeDTO();
        pDTO.setSeq(nSeq);

        NoticeDTO rDTO = Optional.ofNullable(noticeService.getNoticeInfo(pDTO)).orElseGet(NoticeDTO::new);

        log.info(rDTO.toString());
        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".noticeEditInfo End!");

        return "/notice/noticeEditInfo";
    }

    @ResponseBody
    @PostMapping(value = "/noticeUpdate")
    public MsgDTO noticeUpdate(HttpSession session, HttpServletRequest request) {

        log.info(this.getClass().getName() + ".noticeUpdate Start!");

        String msg = "";
        int res = 1;
        MsgDTO dto = null;

        try {
            String user_id = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));
            String title = CmmUtil.nvl(request.getParameter("title"));
            String notice_yn = CmmUtil.nvl(request.getParameter("notice_yn"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));

            log.info("user_id : " + user_id);
            log.info("nSeq : " + nSeq);
            log.info("title : " + title);
            log.info("notice_yn" + notice_yn);
            log.info("contents : " + contents);

            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setSender(user_id);
            pDTO.setSeq(nSeq);
            pDTO.setTitle(title);
            pDTO.setNoticeYn(notice_yn);
            pDTO.setContents(contents);

            noticeService.updateNoticeInfo(pDTO);

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
            log.info(this.getClass().getName() + ".noticeInsert End!");
        }

        return dto;
    }

    @ResponseBody
    @PostMapping(value = "/noticeDelete")
    public MsgDTO noticeDelete(HttpSession session, ModelMap model, HttpServletRequest request) {

        log.info(this.getClass().getName() + ".noticeDelete Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String user_id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String nSeq = CmmUtil.nvl(request.getParameter("seq"));

            log.info("user_id : " + user_id);
            log.info("nSeq : " + nSeq);

            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setSender(user_id);
            pDTO.setSeq(nSeq);
            noticeService.deleteNoticeInfo(pDTO);

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
            log.info(this.getClass().getName() + ".noticeInsert End!");
        }

        return dto;
    }
}

