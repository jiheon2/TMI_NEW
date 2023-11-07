package kopo.poly.controller;


import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.TraderDTO;
import kopo.poly.service.ICustomerService;
import kopo.poly.service.IMailService;
import kopo.poly.service.ITraderService;
import kopo.poly.util.CmmUtil;
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

@Slf4j
@RequestMapping(value = "/mail")
@RequiredArgsConstructor
@Controller
public class MailController {

    private final IMailService mailService;
    private final ITraderService traderService;
    private final ICustomerService customerService;


    @ResponseBody
    @PostMapping(value = "sendMail")
    public MsgDTO sendMail(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".sendMail Start!");

        String msg = "";

        String toMail = CmmUtil.nvl(request.getParameter("toMail"));
        String title = CmmUtil.nvl(request.getParameter("title"));
        String contents = CmmUtil.nvl(request.getParameter("contents"));

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents : " + contents);

        MailDTO pDTO = new MailDTO();

        pDTO.setToMail(toMail);
        pDTO.setTitle(title);
        pDTO.setContents(contents);

        int res = mailService.doSendMail(pDTO);

        if(res == 1) {
            msg = "메일 발송하였습니다";
        }else {
            msg = "메일 발송 실패하였습니다";
        }

        log.info(msg);

        MsgDTO dto = new MsgDTO();
        dto.setMsg(msg);

        log.info(this.getClass().getName() + ".sendMail End!");

        return dto;
    }
    @ResponseBody
    @PostMapping(value = "mailCheck")
    public MsgDTO mailCheck(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".mailCheck Start!");

        String email = CmmUtil.nvl(request.getParameter("email"));
        String type = CmmUtil.nvl(request.getParameter("type"));
        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info("email : " + email);
        log.info("type : " + type);

        int res = 0;

        if (type.equals("Trader")) {
            TraderDTO pDTO = new TraderDTO();
            pDTO.setEmail(email);
            pDTO.setId(id);
            TraderDTO rDTO = traderService.getEmailExists(pDTO);
            if (!rDTO.getExistsYn().isEmpty()) {
                res = 1;
            }
        } else {
            CustomerDTO pDTO = new CustomerDTO();
            pDTO.setEmail(email);
            pDTO.setId(id);
            CustomerDTO rDTO = customerService.getEmailExists(pDTO);
            if (!rDTO.getExistsYn().isEmpty()) {
                res = 1;
            }
        }

        MsgDTO dto = new MsgDTO();
        dto.setResult(res);

        log.info(this.getClass().getName() + ".mailCheck End!");

        return dto;
    }
    @GetMapping(value = "/checkId")
    public String checkId() {

        log.info(this.getClass().getName() + ".checkId");

        return "/mail/checkId";
    }
    @GetMapping(value = "/sendEmail")
    public String sendEmail(HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".checkId");

        model.addAttribute("type", request.getAttribute("type"));

        return "/mail/sendEmail";
    }

}
