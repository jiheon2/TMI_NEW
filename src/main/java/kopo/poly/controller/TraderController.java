package kopo.poly.controller;

import kopo.poly.dto.*;
import kopo.poly.service.IReviewService;
import kopo.poly.service.IShopService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/trader")
@RequiredArgsConstructor
public class TraderController {

    private final ITraderService traderService;
    private final IShopService shopService;

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

            String traderId = CmmUtil.nvl(request.getParameter("traderId")); //아이디
            String traderPw = CmmUtil.nvl(request.getParameter("traderPw")); //비밀번호

            log.info("traderId : " + traderId);
            log.info("traderPw : " + traderPw);

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new TraderDTO();

            pDTO.setTraderId(traderId);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setTraderPw(EncryptUtil.encHashSHA256(traderPw));

            log.info(pDTO.toString());

            // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 traderService 호출하기
            TraderDTO rDTO = traderService.getLogin(pDTO);

            log.info(rDTO.toString());
            if (CmmUtil.nvl(rDTO.getTraderId()).length() > 0) { //로그인 성공

                res = 1;

                msg = "로그인이 성공했습니다.";
                log.info("1");

                session.setAttribute("SS_ID", CmmUtil.nvl(rDTO.getTraderId()));
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
    public String traderSignUp() {
        log.info(this.getClass().getName() + "traderSignUp");
        return "/trader/traderSignUp";
    }

    @ResponseBody
    @PostMapping(value = "getTraderIdExists")
    public TraderDTO getTraderIdExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getTraderIdExists Start!");

        String traderId = CmmUtil.nvl(request.getParameter("traderId"));

        log.info("traderId : " + traderId);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setTraderId(traderId);

        TraderDTO rDTO = Optional.ofNullable(traderService.getTraderIdExists(pDTO)).orElseGet(TraderDTO::new);
        log.info(this.getClass().getName() + ".getTraderIdExists End!");
        return rDTO;
    }
    @ResponseBody
    @PostMapping(value = "getBusinessNumExists")
    public TraderDTO getbusinessNumberExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getBusinessNumb Start!");

        String businessNumber = CmmUtil.nvl(request.getParameter("businessNumber"));

        log.info("businessNumber : " + businessNumber);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setBusinessNumber(businessNumber);

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
            String traderId = CmmUtil.nvl(request.getParameter("traderId"));
            String traderPw = CmmUtil.nvl(request.getParameter("traderPw"));
            String businessNumber = CmmUtil.nvl(request.getParameter("businessNum"));
            String traderName = CmmUtil.nvl(request.getParameter("traderName"));
            String traderPn = CmmUtil.nvl(request.getParameter("traderPn"));

            log.info("traderId : " + traderId);
            log.info("traderPw : " + traderPw);
            log.info("age : " + businessNumber);
            log.info("traderName : " + traderName);
            log.info("traderPn : " + traderPn);

            pDTO = new TraderDTO();

            pDTO.setTraderId(traderId);
            pDTO.setTraderPw(EncryptUtil.encHashSHA256(traderPw));
            pDTO.setPhoneNumber(traderPn);
            pDTO.setTraderName(traderName);
            pDTO.setBusinessNumber(businessNumber);

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
    public String traderIndex(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".traderIndex Start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        ShopDTO pDTO1 = new ShopDTO();

//        pDTO1.setTraderId(traderId);
//        ShopDTO rDTO = Optional.ofNullable(shopService.getCount(pDTO1)).orElseGet(ShopDTO::new);
//        rDTO.setTraderId(traderId);
//
//        ReserveDTO pDTO2 = new ReserveDTO();
//        pDTO2.setTraderId(traderId);
//        pDTO2.setState("1");
//        List<ReserveDTO> rList = Optional.ofNullable(shopService.goodsBuyInfo(pDTO2)).orElseGet(ArrayList::new);
//
//        model.addAttribute("rDTO", rDTO);
//        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".traderIndex End!");
        return "/trader/traderIndex";
    }


    @GetMapping(value = "/traderInfo")
    public String traderInfo(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".traderInfo start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(traderId);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setTraderId(traderId);

        TraderDTO rDTO = Optional.ofNullable(traderService.getTraderInfo(pDTO)).orElseGet(TraderDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".traderInfo start!");
        return "/trader/traderInfo";
    }


    @GetMapping(value = "/reservMng")
    public String reservMng() {
        log.info("start");

        return "/trader/reservMng";
    }


    @GetMapping(value = "/updateTraderInfo")
    public String updateTraderInfo(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".traderInfoChange start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(traderId);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setTraderId(traderId);

        TraderDTO rDTO = Optional.ofNullable(traderService.getTraderInfo(pDTO)).orElseGet(TraderDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".updateTraderInfo End!");
        return "/trader/updateTraderInfo";
    }

    @GetMapping(value = "/updateTraderPw")
    public String updateTraderPw(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".updateTraderPw start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(traderId);

        TraderDTO pDTO = new TraderDTO();

        pDTO.setTraderId(traderId);

        TraderDTO rDTO = Optional.ofNullable(traderService.getTraderInfo(pDTO)).orElseGet(TraderDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".updateTraderPw End!");
        return "/trader/updateTraderPw";
    }
    @ResponseBody
    @PostMapping(value = "updateInfo")
    public MsgDTO updateInfo(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".updateTraderInfo Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        TraderDTO pDTO = null;

        try {
            String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String traderName = CmmUtil.nvl(request.getParameter("traderName"));
            String traderPn = CmmUtil.nvl(request.getParameter("traderPn"));

            log.info("traderId : " + traderId);
            log.info("traderName : " + traderName);
            log.info("traderPn : " + traderPn);

            pDTO = new TraderDTO();

            pDTO.setTraderId(traderId);
            pDTO.setPhoneNumber(traderPn);
            pDTO.setTraderName(traderName);

            log.info(pDTO.toString());

            res = traderService.updateTraderInfo(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "수정되었습니다";
            } else {
                msg = "오류로 인해 수정에 실패하였습니다";
            }
        }catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".updateTraderInfo End!");
        }
        return dto;
    }
    @ResponseBody
    @PostMapping(value = "updatePw")
    public MsgDTO updatePw(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".updatePw Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        TraderDTO pDTO = null;

        try {
            String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String traderPw = CmmUtil.nvl(request.getParameter("traderPw"));

            log.info("traderId : " + traderId);
            log.info("traderPw : " + traderPw);

            pDTO = new TraderDTO();

            pDTO.setTraderId(traderId);
            pDTO.setTraderPw(EncryptUtil.encHashSHA256(traderPw));
            log.info(pDTO.toString());

            res = traderService.updateTraderPw(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "수정되었습니다";
            } else {
                msg = "오류로 인해 수정에 실패하였습니다";
            }
        }catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".updatePw End!");
        }
        return dto;
    }

    @GetMapping(value = "/customerService")
    public String customerService() {
        log.info("start!");

        return "/trader/customerService";
    }
}
