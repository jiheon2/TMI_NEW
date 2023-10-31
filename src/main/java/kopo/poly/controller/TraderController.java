package kopo.poly.controller;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.TraderDTO;
import kopo.poly.service.ITraderService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/trader")
@RequiredArgsConstructor
public class TraderController {

    private final ITraderService traderService;

    @GetMapping(value = "/login")
    public String login() {

        log.info(this.getClass().getName() + ".traderLogin");

        return "/trader/login";
    }

    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) {

        log.info(this.getClass().getName() + ".loginProc Start!");

        int res = 0; //로그인 성공 1, 아이디 비밀번호 불일치 0, 에러 2
        String msg = ""; //결과 메시지
        MsgDTO dto = null;

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        TraderDTO pDTO = null;

        try {

            String id = CmmUtil.nvl(request.getParameter("id")); //아이디
            String pw = CmmUtil.nvl(request.getParameter("pw")); //비밀번호

            log.info("id : " + id);
            log.info("pw : " + pw);

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new TraderDTO();

            pDTO.setId(id);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));

            log.info(pDTO.toString());

            // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 traderService 호출하기
            TraderDTO rDTO = traderService.getLogin(pDTO);

            log.info(rDTO.toString());
            if (CmmUtil.nvl(rDTO.getId()).length() > 0) { //로그인 성공

                res = 1;

                msg = "로그인이 성공했습니다.";
                log.info("1");

                session.setAttribute("SS_ID", CmmUtil.nvl(rDTO.getId()));
                session.setAttribute("SS_TYPE", "Trader");

            } else {
                msg = "아이디와 비밀번호가 올바르지 않습니다.";

            }

        } catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "시스템 문제로 로그인이 실패했습니다.";
            res = 2;
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            // 결과 메시지 전달하기
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".loginProc End!");
        }

        return dto;
    }
    @GetMapping(value = "/traderSignUp")
    public String userSignUp() {
        log.info(this.getClass().getName() + "userSignUp");
        return "/trader/traderSignUp";
    }

    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public TraderDTO getUserIdExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getUserIdExists Start!");

        String id = CmmUtil.nvl(request.getParameter("id"));

        log.info("id : " + id);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setId(id);

        TraderDTO rDTO = Optional.ofNullable(traderService.getUserIdExists(pDTO)).orElseGet(TraderDTO::new);
        log.info(this.getClass().getName() + ".getUserIdExists End!");
        return rDTO;
    }
    @ResponseBody
    @PostMapping(value = "getBusinessNumExists")
    public TraderDTO getBusinessNumExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getBusinessNum Start!");

        String businessNum = CmmUtil.nvl(request.getParameter("businessNum"));

        log.info("businessNum : " + businessNum);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setBusinessNum(businessNum);

        TraderDTO rDTO = Optional.ofNullable(traderService.getBusinessNumExists(pDTO)).orElseGet(TraderDTO::new);
        log.info(this.getClass().getName() + ".getBusinessNumExists End!");
        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "insertTrader")
    public MsgDTO insertTrader(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".insertTrader Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        TraderDTO pDTO = null;

        try {
            String id = CmmUtil.nvl(request.getParameter("id"));
            String pw = CmmUtil.nvl(request.getParameter("pw"));
            String businessNum = CmmUtil.nvl(request.getParameter("businessNum"));
            String shopCode = CmmUtil.nvl(request.getParameter("shopCode"));
            String name = CmmUtil.nvl(request.getParameter("name"));
            String pn = CmmUtil.nvl(request.getParameter("pn"));

            log.info("id : " + id);
            log.info("pw : " + pw);
            log.info("age : " + businessNum);
            log.info("type : " + shopCode);
            log.info("name : " + name);
            log.info("pn : " + pn);

            pDTO = new TraderDTO();

            pDTO.setId(id);
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));
            pDTO.setPn(pn);
            pDTO.setName(name);
            pDTO.setBusinessNum(businessNum);
            pDTO.setShopCode(shopCode);

            log.info(pDTO.toString());

            res = traderService.insertTrader(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다";
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다";
            } else {
                msg = "오류로 인해 회원가입에 실패하였습니다";
            }
        }catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".insertTrader End!");
        }
        return dto;
    }

    @GetMapping(value = "/traderIndex")
    public String traderIndex() {
        log.info("start");

        return "/trader/traderIndex";
    }


    @GetMapping(value = "/traderInfo")
    public String traderInfo(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".traderInfo start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(id);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setId(id);

        TraderDTO rDTO = Optional.ofNullable(traderService.getUserInfo(pDTO)).orElseGet(TraderDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".traderInfo start!");
        return "/trader/traderInfo";
    }


    @GetMapping(value = "/reservMng")
    public String reservMng() {
        log.info("start");

        return "/trader/reservMng";
    }
    @GetMapping(value = "/reviewMng")
    public String reviewMng() {
        log.info("start");

        return "/trader/reviewMng";
    }


    @GetMapping(value = "/traderInfoChange")
    public String traderInfoChange(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".traderInfoChange start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(id);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setId(id);

        TraderDTO rDTO = Optional.ofNullable(traderService.getUserInfo(pDTO)).orElseGet(TraderDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".traderInfo start!");
        return "/trader/traderInfoChange";
    }

    @GetMapping(value = "/changePw")
    public String changePw(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".changePw start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(id);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setId(id);

        TraderDTO rDTO = Optional.ofNullable(traderService.getUserInfo(pDTO)).orElseGet(TraderDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".traderInfo start!");
        return "/trader/changePw";
    }
    @ResponseBody
    @PostMapping(value = "changeTrader")
    public MsgDTO changeTrader(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".changeTrader Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        TraderDTO pDTO = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String shopCode = CmmUtil.nvl(request.getParameter("shopCode"));
            String name = CmmUtil.nvl(request.getParameter("name"));
            String pn = CmmUtil.nvl(request.getParameter("pn"));

            log.info("id : " + id);
            log.info("type : " + shopCode);
            log.info("name : " + name);
            log.info("pn : " + pn);

            pDTO = new TraderDTO();

            pDTO.setId(id);
            pDTO.setPn(pn);
            pDTO.setName(name);
            pDTO.setShopCode(shopCode);

            log.info(pDTO.toString());

            res = traderService.changeTrader(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "수정되었습니다";
            } else {
                msg = "오류로 인해 회원가입에 실패하였습니다";
            }
        }catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".changeTrader End!");
        }
        return dto;
    }
    @ResponseBody
    @PostMapping(value = "pwChange")
    public MsgDTO pwChange(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".pwChange Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        TraderDTO pDTO = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String pw = CmmUtil.nvl(request.getParameter("npw"));

            log.info("id : " + id);
            log.info("pw : " + pw);

            pDTO = new TraderDTO();

            pDTO.setId(id);
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));
            log.info(pDTO.toString());

            res = traderService.changePw(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "수정되었습니다";
            } else {
                msg = "오류로 인해 회원가입에 실패하였습니다";
            }
        }catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".pwChange End!");
        }
        return dto;
    }

    @GetMapping(value = "/customerService")
    public String customerService() {
        log.info("start!");

        return "/trader/customerService";
    }
}
