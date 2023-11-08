package kopo.poly.controller;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.CustomerDTO;
import kopo.poly.service.ICustomerService;
import kopo.poly.service.IMailService;
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
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;
    private final IMailService mailService;
    @GetMapping(value = "/login")
    public String login(HttpSession session) {


        return "/customer/login";
    }
    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) {

        log.info(this.getClass().getName() + ".loginProc Start!");

        int res = 0; //로그인 성공 1, 아이디 비밀번호 불일치 0, 에러 2
        String msg = ""; //결과 메시지
        MsgDTO dto = null;

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        CustomerDTO pDTO = null;

        try {

            String id = CmmUtil.nvl(request.getParameter("id")); //아이디
            String pw = CmmUtil.nvl(request.getParameter("pw")); //비밀번호

            log.info("id : " + id);
            log.info("pw : " + pw);

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new CustomerDTO();

            pDTO.setId(id);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));

            log.info(pDTO.toString());

            // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 customerService 호출하기
            CustomerDTO rDTO = customerService.getLogin(pDTO);

            log.info(rDTO.toString());
            if (CmmUtil.nvl(rDTO.getId()).length() > 0) { //로그인 성공

                res = 1;

                msg = "로그인이 성공했습니다.";

                session.setAttribute("SS_ID", CmmUtil.nvl(rDTO.getId()));

                session.setAttribute("SS_TYPE", "Customer");

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


    @GetMapping(value = "/customerIndex")
    public String customerIndex() {
        log.info("start!");

        return "/customer/customerIndex";
    }

    @GetMapping(value = "/cart")
    public String cart() {
        log.info("start!");
        return "/customer/cart";
    }

    @GetMapping(value = "/customerSignUp")
    public String customerSignUp() {
        log.info(this.getClass().getName() + "customerSignUp");
        return "/customer/customerSignUp";
    }

    @ResponseBody
    @PostMapping(value = "getCustomerIdExists")
    public CustomerDTO getCustomerIdExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getCustomerIdExists Start!");

        String id = CmmUtil.nvl(request.getParameter("id"));

        log.info("id : " + id);

        CustomerDTO pDTO = new CustomerDTO();

        pDTO.setId(id);

        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerIdExists(pDTO)).orElseGet(CustomerDTO::new);
        log.info(this.getClass().getName() + ".getCustomerIdExists End!");
        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "insertCustomer")
    public MsgDTO insertCustomer(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".insertCustomer Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        CustomerDTO pDTO = null;

        try {
            String id = CmmUtil.nvl(request.getParameter("id"));
            String pw = CmmUtil.nvl(request.getParameter("pw"));
            String pn = CmmUtil.nvl(request.getParameter("pn"));
            String name = CmmUtil.nvl(request.getParameter("name"));
            String age = CmmUtil.nvl(request.getParameter("age"));
            String type = CmmUtil.nvl(request.getParameter("type"));

            log.info("id : " + id);
            log.info("pw : " + pw);
            log.info("pn : " + pn);
            log.info("name : " + name);
            log.info("age : " + age);
            log.info("type : " + type);

            pDTO = new CustomerDTO();

            pDTO.setId(id);
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));
            pDTO.setPn(pn);
            pDTO.setName(name);
            pDTO.setAge(age);
            pDTO.setType(type);

            log.info(pDTO.toString());

            res = customerService.insertCustomer(pDTO);

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
            log.info(this.getClass().getName() + ".insertCustomer End!");
        }
        return dto;
    }
    @GetMapping(value = "/shop")
    public String shop() {
        log.info(this.getClass().getName() + ".shop Start!");
        return "/customer/shop";
    }

    @GetMapping(value = "/map")
    public String map() {
        log.info("start!");
        return "/customer/map";
    }

    @GetMapping(value = "/market")
    public String market() {
        log.info("start!");
        return "/customer/market";
    }

    @GetMapping(value = "/chat")
    public String chat() {
        log.info("start!");
        return "/customer/chat";
    }

    @GetMapping(value = "/customerInfo")
    public String getCustomerInfo(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".customerLogin");

        String type = (String) session.getAttribute("SS_TYPE");
        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        String url = "/customer/customerInfo";
        if(!type.equals("Customer")) {
            session.invalidate();
            url = "/customer/login";
        }
        CustomerDTO pDTO = new CustomerDTO();
        pDTO.setId(id);
        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerInfo(pDTO)).orElseGet(CustomerDTO::new);
        model.addAttribute("rDTO", rDTO);
        return url;

    }
    @GetMapping(value = "/customerInfoChange")
    public String customerInfoChange(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".customerInfoChange start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(id);

        CustomerDTO pDTO = new CustomerDTO();

        pDTO.setId(id);

        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerInfo(pDTO)).orElseGet(CustomerDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".customerInfo start!");
        return "/customer/customerInfoChange";
    }
    @GetMapping(value = "searchId")
    public String searchCustomerId() {
        log.info(this.getClass().getName() + ".customer/searchCustomerId Start!");

        log.info(this.getClass().getName() + ".customer/searchCustomerId End!");

        return "customer/searchId";

    }
    @GetMapping(value = "searchPw")
    public String searchPassword(HttpSession session) {
        log.info(this.getClass().getName() + ".customer/searchPassword Start!");

        // 강제 URL 입력 등 오는 경우가 있어 세션 삭제
        // 비밀번호 재생성하는 화면은 보안을 위해 생성한 NEW_PASSWORD 세션 삭제
        session.setAttribute("NEW_PASSWORD", "");
        session.removeAttribute("NEW_PASSWORD");

        log.info(this.getClass().getName() + ".customer/searchPassword End!");

        return "customer/searchPw";

    }
 

    @PostMapping(value = "searchCustomerIdProc")
    public String searchCustomerIdProc(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".Customer/searchCustomerIdProc Start!");

        String CustomerName = CmmUtil.nvl(request.getParameter("userName")); // 이름
        String email = CmmUtil.nvl(request.getParameter("email")); // 이메일


        log.info("CustomerName : " + CustomerName);
        log.info("email : " + email);


        CustomerDTO pDTO = new CustomerDTO();
        pDTO.setName(CustomerName);
        pDTO.setEmail(email);

        CustomerDTO rDTO = Optional.ofNullable(customerService.searchCustomerIdOrPasswordProc(pDTO))
                .orElseGet(CustomerDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".Customer/searchCustomerIdProc End!");

        return "customer/showId";

    }
    @PostMapping(value = "searchPasswordProc")
    public String searchPasswordProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".Customer/searchPasswordProc Start!");

        String CustomerId = CmmUtil.nvl(request.getParameter("id")); // 아이디
        String CustomerName = CmmUtil.nvl(request.getParameter("name")); // 이름
        String email = CmmUtil.nvl(request.getParameter("email")); // 이메일

        log.info("CustomerId : " + CustomerId);
        log.info("CustomerName : " + CustomerName);
        log.info("email : " + email);

        CustomerDTO pDTO = new CustomerDTO();
        pDTO.setId(CustomerId);
        pDTO.setName(CustomerName);
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        // 비밀번호 찾기 가능한지 확인하기
        CustomerDTO rDTO = Optional.ofNullable(customerService.searchCustomerIdOrPasswordProc(pDTO)).orElseGet(CustomerDTO::new);

        model.addAttribute("rDTO", rDTO);

        // 비밀번호 재생성하는 화면은 보안을 위해 반드시 NEW_PASSWORD 세션이 존재해야 접속 가능하도록 구현
        // CustomerId 값을 넣은 이유는 비밀번호 재설정하는 newPasswordProc 함수에서 사용하기 위함
        session.setAttribute("NEW_PASSWORD", CustomerId);

        log.info(this.getClass().getName() + ".Customer/searchPasswordProc End!");

        return "customer/changePw";

    }
    @ResponseBody
    @PostMapping(value = "searchEmail")
    public CustomerDTO searchEmail(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".searchEmail Start!");

        String email = CmmUtil.nvl(request.getParameter("email")); // 회원아이디

        log.info("email : " + email);

        CustomerDTO pDTO = new CustomerDTO();
        pDTO.setEmail(email);

        // 입력된 이메일이 중복된 이메일인지 조회
        CustomerDTO rDTO = Optional.ofNullable(customerService.searchEmail(pDTO)).orElseGet(CustomerDTO::new);

        log.info(this.getClass().getName() + ".searchEmail End!");

        return rDTO;
    }
    @GetMapping(value = "/changePw")
    public String changePw(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".changePw start!");

        String id = CmmUtil.nvl((String) session.getAttribute("NEW_PASSWORD"));

        log.info(id);

        CustomerDTO pDTO = new CustomerDTO();

        pDTO.setId(id);

        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerInfo(pDTO)).orElseGet(CustomerDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".customerInfo start!");
        return "/customer/changePw";
    }
    @ResponseBody
    @PostMapping(value = "changeCustomer")
    public MsgDTO changeCustomer(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".changeCustomer Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        CustomerDTO pDTO = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String age = CmmUtil.nvl(request.getParameter("age"));
            String type = CmmUtil.nvl(request.getParameter("type"));
            String pn = CmmUtil.nvl(request.getParameter("pn"));
            String name = CmmUtil.nvl(request.getParameter("name"));


            log.info("id : " + id);
            log.info("age : " + age);
            log.info("type : " + type);
            log.info("pn : " + pn);

            pDTO = new CustomerDTO();

            pDTO.setId(id);
            pDTO.setPn(pn);
            pDTO.setName(name);
            pDTO.setType(type);
            pDTO.setAge(age);

            log.info(pDTO.toString());

            res = customerService.changeCustomer(pDTO);

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
            log.info(this.getClass().getName() + ".changeCustomer End!");
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

        CustomerDTO pDTO = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("NEW_PASSWORD"));
            String pw = CmmUtil.nvl(request.getParameter("npw"));

            log.info("id : " + id);
            log.info("pw : " + pw);

            pDTO = new CustomerDTO();

            pDTO.setId(id);
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));
            log.info(pDTO.toString());

            res = customerService.changePw(pDTO);

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

    @GetMapping(value = "/single-product")
    public String singleProduct() {
        log.info("start!");
        return "/customer/single-product";
    }
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


}
