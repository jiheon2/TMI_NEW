package kopo.poly.controller;

import kopo.poly.dto.*;
import kopo.poly.persistance.mapper.IBasketMapper;
import kopo.poly.service.*;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
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
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;
    private final IShopService shopService;
    private final IGoodsService goodsService;
    private final IReviewService reviewService;
    private final IBasketService basketService;

    private final IPostService postService;
    private final IReservationService reservationService;

    @GetMapping(value = "/login")
    public String login(HttpSession session) {
        return "/customer/login";
    }

    // 소비자 로그인 로직 코드
    // 구현완료(11/10)
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

            String customerId = CmmUtil.nvl(request.getParameter("customerId")); //아이디
            String customerPw = CmmUtil.nvl(request.getParameter("customerPw")); //비밀번호

            log.info("customerId : " + customerId);
            log.info("customerPw : " + customerPw);

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new CustomerDTO();

            pDTO.setCustomerId(customerId);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setCustomerPw(EncryptUtil.encHashSHA256(customerPw));

            log.info(pDTO.toString());

            // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 customerService 호출하기
            CustomerDTO rDTO = customerService.getLogin(pDTO);

            log.info(rDTO.toString());
            if (CmmUtil.nvl(rDTO.getCustomerId()).length() > 0) { //로그인 성공

                res = 1;

                msg = "로그인이 성공했습니다.";

                if(rDTO.getReward() == 0) {
                    customerService.pointReward(pDTO); // 10포인트 추가
                }

                session.setAttribute("SS_ID", CmmUtil.nvl(rDTO.getCustomerId()));

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

    // 소비자 메인페이지 이동코드
    // 구현완료(11/13)
    @GetMapping(value = "/customerIndex")
    public String customerIndex(ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".customerIndex Start!");

        String type = "verification";
        String market = "";

        List<PostDTO> pList = Optional.ofNullable(postService.getPostList(type)).orElseGet(ArrayList::new);
        List<GoodsDTO> gList = Optional.ofNullable(reservationService.getPopularGoods(market)).orElseGet(ArrayList::new);
        List<MarketDTO> mList = Optional.ofNullable(reservationService.getPopularMarket()).orElseGet(ArrayList::new);

        log.info(pList.toString());
        log.info(gList.toString());
        log.info(mList.toString());

        model.addAttribute("pList",pList);
        model.addAttribute("gList",gList);
        model.addAttribute("mList",mList);

        return "/customer/customerIndex";
    }

    // 소비자 장바구니 이동코드
    @GetMapping(value = "/cart")
    public String cart(HttpSession session, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".cart Start!");

        String customerId = (String) session.getAttribute("SS_ID");

        BasketDTO pDTO = new BasketDTO();

        pDTO.setCustomerId(customerId);

        List<BasketDTO> rList = Optional.ofNullable(basketService.getBasketList(pDTO)).orElseGet(ArrayList::new);
        CustomerDTO pDTO1 = new CustomerDTO();
        pDTO.setCustomerId(customerId);
        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerInfo(pDTO1)).orElseGet(CustomerDTO::new);

        log.info(rList.toString());

        model.addAttribute("rList", rList);
        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".cart Start!");
        return "/customer/cart";
    }

    // 소비자 회원가입페이지 이동코드
    // 구현완료(11/13)
    @GetMapping(value = "/customerSignUp")
    public String customerSignUp() {
        log.info(this.getClass().getName() + "customerSignUp");

        return "/customer/customerSignUp";
    }

    // 소비자 ID 중복확인 로직코드
    // 구현완료(11/13)
    @ResponseBody
    @PostMapping(value = "getCustomerIdExists")
    public CustomerDTO getCustomerIdExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getCustomerIdExists Start!");

        String customerId = CmmUtil.nvl(request.getParameter("customerId"));

        log.info("customerId : " + customerId);

        CustomerDTO pDTO = new CustomerDTO();

        pDTO.setCustomerId(customerId);

        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerIdExists(pDTO)).orElseGet(CustomerDTO::new);
        log.info(rDTO.toString());

        log.info(this.getClass().getName() + ".getCustomerIdExists End!");

        return rDTO;
    }


    // 소비자 회원가입로직 코드
    // 구현완료(11/13)
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
            String customerId = CmmUtil.nvl(request.getParameter("customerId"));
            String customerPw = CmmUtil.nvl(request.getParameter("customerPw"));
            String customerPn = CmmUtil.nvl(request.getParameter("phoneNumber"));
            String customerName = CmmUtil.nvl(request.getParameter("customerName"));
            String customerEmail = CmmUtil.nvl(request.getParameter("customerEmail"));
            
            log.info("customerId : " + customerId);
            log.info("customerPw : " + customerPw);
            log.info("customerPn : " + customerPn);
            log.info("customerName : " + customerName);
            log.info("customerEmail : " + customerEmail);

            pDTO = new CustomerDTO();

            pDTO.setCustomerId(customerId);
            pDTO.setCustomerPw(EncryptUtil.encHashSHA256(customerPw));
            pDTO.setPhoneNumber(customerPn);
            pDTO.setCustomerName(customerName);
            pDTO.setCustomerEmail(customerEmail);

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
        } catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".insertCustomer End!");
        }
        return dto;
    }

    // 상점정보 조회 코드
    // 구현중(11/14)
    @GetMapping(value = "/shop")
    public String shop(HttpServletRequest request, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".shop Start!");
        String shopNumber = request.getParameter("shopNumber");

        GoodsDTO pDTO = new GoodsDTO();

        pDTO.setShopNumber(shopNumber);

        List<GoodsDTO> rList = Optional.ofNullable(goodsService.getGoodsList(pDTO)).orElseGet(ArrayList::new);
        List<ShopDTO> sList = Optional.ofNullable(reservationService.getPopularShop()).orElseGet(ArrayList::new);

        log.info(rList.toString());
        String shopName;
        String shopDescription;
        String market;
        String goodsCount;
        if (!rList.isEmpty()) {
            GoodsDTO firstGoods = rList.get(0);
            shopName = firstGoods.getShopName();
            shopDescription = firstGoods.getShopDescription();
            market = firstGoods.getMarketNumber();
            goodsCount = firstGoods.getGoodsCount();
        } else {
            shopName = "아직 이 상점에는 상품이 없어요";
            shopDescription = "";
            market = "";
            goodsCount = "0";
        }

        model.addAttribute("goodsCount", goodsCount);
        model.addAttribute("rList", rList);
        model.addAttribute("sList", sList);
        model.addAttribute("shopName", shopName);
        model.addAttribute("shopDescription", shopDescription);
        model.addAttribute("market", market);
        log.info(this.getClass().getName() + ".shop End!");

        return "/customer/shop";
    }


    // 지도페이지 이동코드
    // 구현완료(10/24)
    @GetMapping(value = "/map")
    public String map() {
        log.info("start!");
        return "/customer/map";
    }

    // 시장정보 조회 코드
    // 구현중 (11/14)
    @GetMapping(value = "/market")
    public String market(HttpServletRequest request, ModelMap model) throws Exception{
        log.info("start!");

        String market = request.getParameter("marketNumber");

        log.info("marketNumber : " + market);

        ShopDTO pDTO = new ShopDTO();

        pDTO.setMarketNumber(market);

        List<ShopDTO> rList = Optional.ofNullable(shopService.getShopList(pDTO)).orElseGet(ArrayList::new);

        String marketName;
        String shopCount;
        if (!rList.isEmpty()) {
            ShopDTO firstShop = rList.get(0);
            marketName = firstShop.getMarketName();
            shopCount = firstShop.getShopCount();
        } else {
            marketName = "아직 이 시장에는 상점이 없어요";
            shopCount = "0";
        }

        model.addAttribute("shopCount", shopCount);
        model.addAttribute("marketName", marketName);
        model.addAttribute("rList", rList);

        return "/customer/market";
    }


    // 채팅페이지 이동코드
    @GetMapping(value = "/chat")
    public String chat() {
        log.info("start!");
        return "/customer/chat";
    }

    // 소비자 마이페이지 이동코드
    // 구현완료(11/14)
    @GetMapping(value = "/customerInfo")
    public String getCustomerInfo(HttpSession session, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".customerLogin");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String type = CmmUtil.nvl((String) session.getAttribute("Customer"));

        String url = "/customer/customerInfo";
        if (customerId.equals(null)) {
            url = "/customer/login";
        }

        CustomerDTO pDTO = new CustomerDTO();
        pDTO.setCustomerId(customerId);
        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerInfo(pDTO)).orElseGet(CustomerDTO::new);
        model.addAttribute("rDTO", rDTO);
        return url;

    }

    // 소비자 정보 수정페이지 이동코드
    // 구현완료(11/10)
    @GetMapping(value = "/updateCustomerInfo")
    public String updateCustomerInfo(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".updateCustomerInfo start!");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(customerId);

        CustomerDTO pDTO = new CustomerDTO();

        pDTO.setCustomerId(customerId);

        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerInfo(pDTO)).orElseGet(CustomerDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".updateCustomerInfo start!");
        return "/customer/updateCustomerInfo";
    }

    // 소비자 비밀번호 수정 페이지 이동코드
    // 구현완료(11/10)
    @GetMapping(value = "/updateCustomerPw")
    public String updateCustomerPw(HttpSession session, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".updateCustomerPw start!");

        String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        log.info(customerId);

        CustomerDTO pDTO = new CustomerDTO();

        pDTO.setCustomerId(customerId);

        CustomerDTO rDTO = Optional.ofNullable(customerService.getCustomerInfo(pDTO)).orElseGet(CustomerDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".updateCustomerPw End!");
        return "/customer/updateCustomerPw";
    }

    // 소비자 정보 수정 로직코드
    // 구현완료(11/10)
    @ResponseBody
    @PostMapping(value = "updateInfo")
    public MsgDTO updateInfo(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".updateInfo Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        CustomerDTO pDTO = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String customerEmail = CmmUtil.nvl(request.getParameter("customerEmail"));
            String customerPn = CmmUtil.nvl(request.getParameter("customerPn"));
            String customerName = CmmUtil.nvl(request.getParameter("customerName"));


            log.info("customerId : " + customerId);
            log.info("customerEmail : " + customerEmail);
            log.info("customerPn : " + customerPn);

            pDTO = new CustomerDTO();

            pDTO.setCustomerId(customerId);
            pDTO.setPhoneNumber(customerPn);
            pDTO.setCustomerName(customerName);
            pDTO.setCustomerEmail(customerEmail);

            log.info(pDTO.toString());

            res = customerService.updateCustomerInfo(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "수정되었습니다";
            } else {
                msg = "오류로 인해 수정에 실패하였습니다";
            }
        } catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".updateInfo End!");
        }
        return dto;
    }

    // 소비자 비밀번호 수정 코드
    // 구현완료(11/10)
    @ResponseBody
    @PostMapping(value = "updatePw")
    public MsgDTO updatePw(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".updatePw Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        CustomerDTO pDTO = null;

        try {
            String customerId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String customerPw = CmmUtil.nvl(request.getParameter("customerPw"));

            log.info("customerId : " + customerId);
            log.info("customerPw : " + customerPw);

            pDTO = new CustomerDTO();

            pDTO.setCustomerId(customerId);
            pDTO.setCustomerPw(EncryptUtil.encHashSHA256(customerPw));
            log.info(pDTO.toString());

            res = customerService.updateCustomerPw(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "수정되었습니다";
            } else {
                msg = "오류로 인해 회원가입에 실패하였습니다";
            }
        } catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".updatePw End!");
        }
        return dto;
    }

    // 상품 상세정보 조회페이지 이동코드
    @GetMapping(value = "/single-product")
    public String singleProduct(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".goodsMngInfo Start!");

        String goodsNumber = request.getParameter("goodsNumber");
        String market = request.getParameter("market");
        log.info("goodsNumber : " + goodsNumber);
        log.info("market : " + market);

        GoodsDTO pDTO = new GoodsDTO();
        pDTO.setGoodsNumber(goodsNumber);
        GoodsDTO gDTO = Optional.ofNullable(goodsService.getGoodsInfo(pDTO)).orElseGet(GoodsDTO::new);
        List<GoodsDTO> gList = Optional.ofNullable(reservationService.getPopularGoods(market)).orElseGet(ArrayList::new);

        ReviewDTO pDTO2 = new ReviewDTO();
        pDTO2.setGoodsNumber(goodsNumber);
        List<ReviewDTO> rDTO = Optional.ofNullable(reviewService.oneReviewList(pDTO2)).orElseGet(ArrayList::new);
        List<ReviewDTO> cDTO = Optional.ofNullable(reviewService.getScore(pDTO2)).orElseGet(ArrayList::new);

        log.info("gDTO : " + gDTO.toString());
        log.info("rDTO : " + rDTO.toString());
        log.info("cDTO : " + cDTO.toString());
        log.info("gList : " + gList.toString());

        model.addAttribute("cDTO", cDTO);
        model.addAttribute("rDTO", rDTO);
        model.addAttribute("gDTO", gDTO);
        model.addAttribute("gList", gList);

        log.info(this.getClass().getName() + ".goodsMngInfo End!");
        return "/customer/single-product";
    }
}
