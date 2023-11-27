package kopo.poly.controller;

import kopo.poly.dto.GoodsDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.ReservationDTO;
import kopo.poly.service.IFileService;
import kopo.poly.service.IGoodsService;
import kopo.poly.service.IShopService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GoodsController {
    private final IGoodsService goodsService;
    private final IFileService fileService;
    private final IShopService shopService;

    /*
        상품 정보 관리 페이지 접속 코드
        구현완료(11/14)
     */
    @GetMapping(value = "/goods/goodsMng")
    public String goodsMng(ModelMap model, HttpSession session, @RequestParam(defaultValue = "1") int page) throws Exception {

        log.info(this.getClass().getName() + ".goodsMng Start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        if (traderId.equals(null)) {
            return "trader/login";
        }

        GoodsDTO pDTO = new GoodsDTO();
        pDTO.setTraderId(traderId);

        log.info("traderId : " + traderId);

        List<GoodsDTO> rList = goodsService.getGoodsList(pDTO);
        if (rList == null) rList = new ArrayList<>();

        // 페이지당 보여줄 아이템 개수 정의
        int itemsPerPage = 3;

        // 페이지네이션을 위해 전체 아이템 개수 구하기
        int totalItems = rList.size();

        // 전체 페이지 개수 계산
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        // 현재 페이지에 해당하는 아이템들만 선택하여 rList에 할당
        int fromIndex = (page - 1) * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, totalItems);
        rList = rList.subList(fromIndex, toIndex);

        model.addAttribute("rList", rList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        log.info(this.getClass().getName() + ".페이지 번호 : " + page);
        log.info(this.getClass().getName() + ".goodsMng End!");

        return "/goods/goodsMng";
    }

    /*
        상품 상세 정보 코드
        구현완료(11/14)
     */
    @GetMapping(value = "/goods/goodsMngInfo")
    public String goodsMngInfo(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".goodsMngInfo Start!");

        String traderId = (String) session.getAttribute("SS_ID");
        String goodsNumber = (String) request.getParameter("goodsNumber");

        String url = "/goods/goodsMngInfo";

        GoodsDTO pDTO = new GoodsDTO();
        pDTO.setGoodsNumber(goodsNumber);
        GoodsDTO rDTO = Optional.ofNullable(goodsService.getGoodsInfo(pDTO)).orElseGet(GoodsDTO::new);
        model.addAttribute("rDTO", rDTO);
        log.info(rDTO.toString());

        log.info(this.getClass().getName() + ".goodsMngInfo End!");

        return url;
    }

    /*
        상품 정보 등록 페이지 이동 코드
        구현완료(11/14)
     */
    @GetMapping(value = "/goods/goodsMngInsert")
    public String goodsMngInsert(HttpSession session, ModelMap model, HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".goodsMngInsert start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String goodsNumber = CmmUtil.nvl(request.getParameter("goodsNumber"));
        log.info(traderId);

        GoodsDTO pDTO = new GoodsDTO();

        pDTO.setTraderId(traderId);
        pDTO.setGoodsNumber(goodsNumber);

        log.info(pDTO.toString());
        GoodsDTO rDTO = Optional.ofNullable(goodsService.getGoodsInfo(pDTO)).orElseGet(GoodsDTO::new);

        log.info(rDTO.toString());
        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".goodsMngInsert start!");
        return "/goods/goodsMngInsert";
    }

    /*
        상품 정보 업데이트 페이지 이동 코드
        구현완료(11/14)
     */
    @GetMapping(value = "/goods/goodsMngUpdate")
    public String goodsMngUpdate(HttpSession session, ModelMap model, HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".goodsMngUpdate start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String goodsNumber = CmmUtil.nvl(request.getParameter("goodsNumber"));
        log.info(traderId);

        GoodsDTO pDTO = new GoodsDTO();

        pDTO.setTraderId(traderId);
        pDTO.setGoodsNumber(goodsNumber);

        log.info(pDTO.toString());
        GoodsDTO rDTO = Optional.ofNullable(goodsService.getGoodsInfo(pDTO)).orElseGet(GoodsDTO::new);

        log.info(rDTO.toString());
        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".goodsMngUpdate start!");
        return "/goods/goodsMngUpdate";
    }


    /*
        상품 정보 업데이트 로직 실행 코드
        구현완료(11/14)
     */
    @ResponseBody
    @PostMapping(value = "/goods/updateGoods")
    public MsgDTO updateGoods(HttpServletRequest request, @RequestParam(value = "fileUpload") MultipartFile mf, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".updateGoods Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        GoodsDTO pDTO = null;

        try {
            String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String goodsName = CmmUtil.nvl(request.getParameter("goodsName"));
            String price = CmmUtil.nvl(request.getParameter("price"));
            String goodsDescription = CmmUtil.nvl(request.getParameter("goodsDescription"));
            String goodsType = CmmUtil.nvl(request.getParameter("goodsType"));
            String goodsNumber = CmmUtil.nvl(request.getParameter("goodsNumber"));

            log.info("price:" + price);
            log.info("goodsDescription:" + goodsDescription);
            log.info("goodsName : " + goodsName);
            log.info("goodsType : " + goodsType);
            log.info("goodsNumber : " + goodsNumber);

            pDTO = new GoodsDTO();

            pDTO.setPrice(price);
            pDTO.setGoodsDescription(goodsDescription);
            pDTO.setGoodsType(goodsType);
            pDTO.setTraderId(traderId);
            pDTO.setGoodsName(goodsName);
            pDTO.setGoodsNumber(goodsNumber);
            pDTO.setGoodsImage("");

            if (!mf.isEmpty()) {
                String image = mf.getOriginalFilename();
                String fileName = image;
                String folderName = "Trader" + "/" + pDTO.getTraderId() + "/" + "Goods" + "/";
                fileService.upload(fileName, folderName, mf);
                pDTO.setGoodsImage(fileService.getFileURL(folderName, fileName));
                log.info(pDTO.getGoodsImage());
            }
            log.info(pDTO.toString());

            res = goodsService.updateGoods(pDTO);

            log.info("res : " + res);


            msg = "수정하였습니다";
        } catch (Exception e) {
            msg = "수정 실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".updateGoods End!");
        }
        return dto;
    }

    /*
        상품정보 등록 로직 실행코드
        구현완료(11/14)
     */
    @ResponseBody
    @PostMapping(value = "/goods/insertGoods")
    public MsgDTO insertGoods(HttpServletRequest request, @RequestParam(value = "fileUpload") MultipartFile mf, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".insertGoods Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        GoodsDTO pDTO = null;

        try {
            String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String goodsName = CmmUtil.nvl(request.getParameter("goodsName"));
            String price = CmmUtil.nvl(request.getParameter("price"));
            String goodsDescription = CmmUtil.nvl(request.getParameter("goodsDescription"));
            String goodsType = CmmUtil.nvl(request.getParameter("goodsType"));
            String goodsNumber = CmmUtil.nvl(request.getParameter("goodsNumber"));

            log.info("price:" + price);
            log.info("goodsDescription:" + goodsDescription);
            log.info("goodsName : " + goodsName);
            log.info("goodsType : " + goodsType);
            log.info("goodsNumber : " + goodsNumber);

            pDTO = new GoodsDTO();

            pDTO.setPrice(price);
            pDTO.setGoodsDescription(goodsDescription);
            pDTO.setGoodsType(goodsType);
            pDTO.setTraderId(traderId);
            pDTO.setGoodsName(goodsName);
            pDTO.setGoodsNumber(goodsNumber);
            pDTO.setGoodsImage("");

            if (!mf.isEmpty()) {
                String image = mf.getOriginalFilename();
                String fileName = image;
                String folderName = "Trader" + "/" + pDTO.getTraderId() + "/" + "Goods" + "/";
                fileService.upload(fileName, folderName, mf);
                pDTO.setGoodsImage(fileService.getFileURL(folderName, fileName));
                log.info(pDTO.getGoodsImage());
            }
            log.info(pDTO.toString());

            res = goodsService.insertGoods(pDTO);

            log.info("res : " + res);

            msg = "등록하였습니다";

        } catch (Exception e) {
            msg = "등록 실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".insertGoods End!");
        }
        return dto;
    }


    /*
        상품삭제 로직 실행코드
        구현 완료(11/14)
     */
    @ResponseBody
    @PostMapping(value = "/goods/goodsMngDelete")
    public MsgDTO goodsMngDelete(HttpSession session, HttpServletRequest request) {

        log.info(this.getClass().getName() + ".goodsMngDelete Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String trader_id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String goodsNumber = CmmUtil.nvl(request.getParameter("goodsNumber"));

            log.info("trader_id : " + trader_id);
            log.info("goodsNumber : " + goodsNumber);

            GoodsDTO pDTO = new GoodsDTO();
            pDTO.setTraderId(trader_id);
            pDTO.setGoodsNumber(goodsNumber);
            goodsService.goodsInfoDelete(pDTO);

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
            log.info(this.getClass().getName() + ".goodsMngDelete End!");
        }

        return dto;
    }
    @GetMapping(value = "/goods/goodsBuyInfo")
    public String goodsBuyInfo(ModelMap model, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + ".goodsBuyInfo Start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        ReservationDTO pDTO = new ReservationDTO();
        pDTO.setTraderId(id);
        pDTO.setState("0");
        List<ReservationDTO> rList = shopService.goodsBuyInfo(pDTO);
        if (rList == null) rList = new ArrayList<>();

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".goodsBuyInfo End!");

        return "/goods/goodsBuyInfo";
    }
    @ResponseBody
    @PostMapping(value = "/goods/acceptBuy")
    public MsgDTO acceptBuy(HttpSession session,@RequestBody Map<String, Object> requestData) {
        log.info(this.getClass().getName() + ".acceptBuy Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String want = (String) requestData.get("want");
            List<String> checkboxes = (List<String>) requestData.get("selectedSeqs");
            log.info(want);
            log.info("id : " + id);
            log.info("checkboxes : " + checkboxes);
            ReservationDTO pDTO = new ReservationDTO();
            if (want.equals("delete")) {
                for (String seq : checkboxes) {
                    pDTO.setReservationNumber(seq);
                    pDTO.setTraderId(id);

                    shopService.deleteBuy(pDTO);
                }
                msg = "삭제되었습니다.";
            } else {
                for (String seq : checkboxes) {
                    pDTO.setReservationNumber(seq);
                    pDTO.setTraderId(id);

                    shopService.acceptBuy(pDTO);
                }
                msg = "수락하였습니다.";
            }

            res = 1;
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);
            log.info(this.getClass().getName() + ".deleteBuy End!");
        }

        return dto;
    }
}
