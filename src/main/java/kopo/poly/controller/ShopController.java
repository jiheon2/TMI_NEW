package kopo.poly.controller;


import kopo.poly.dto.*;
import kopo.poly.service.IFileService;
import kopo.poly.service.IMarketService;
import kopo.poly.service.IShopService;
import kopo.poly.service.ITraderService;
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
public class ShopController {

    private final IShopService shopService;
    private final IFileService fileService;
    private final IMarketService marketService;
    private final ITraderService traderService;

    // 상점 정보 페이지 이동코드
    // 상인 정보 없을 시 로그인페이지로 리턴 구현하기
    // 구현완료(11/14)
    @GetMapping(value = "/trader/shopInfo")
    public String shopInfo(HttpSession session, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".shopInfo Start!");

        String url = "";

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        if (traderId.equals(null)) {
            url = "/trader/login";
        }

        ShopDTO pDTO = new ShopDTO();

        pDTO.setTraderId(traderId);

        ShopDTO rDTO = Optional.ofNullable(shopService.getShopInfo(pDTO)).orElseGet(ShopDTO::new);
        if (rDTO.getShopName() == null || rDTO.getShopName().isEmpty()) {
            TraderDTO pDTO1 = new TraderDTO();
            pDTO1.setTraderId(traderId);
            TraderDTO tDTO = Optional.ofNullable(traderService.getTraderInfo(pDTO1)).orElseGet(TraderDTO::new);

            MarketDTO pDTO2 = new MarketDTO();
            pDTO2.setMarketNumber(tDTO.getShopCode());
            log.info(tDTO.getShopCode());

            MarketDTO mDTO = Optional.ofNullable(marketService.getMarket(pDTO2)).orElseGet(MarketDTO::new);

            model.addAttribute("mDTO", mDTO);

            url = "/trader/insertShopInfo";
        } else {
            model.addAttribute("rDTO", rDTO);
            url = "/trader/shopInfo";
        }


        log.info(this.getClass().getName() + ".shopInfo End!");

        return url;
    }

    // 상인 정보 수정페이지 이동코드
    // 구현완료(11/13)
    @GetMapping(value = "/trader/updateShopInfo")
    public String updateShopInfo(HttpSession session, ModelMap model, HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".updateShopInfo start!");

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        log.info(traderId);

        ShopDTO pDTO = new ShopDTO();

        pDTO.setTraderId(traderId);

        log.info(pDTO.toString());
        ShopDTO rDTO = Optional.ofNullable(shopService.getShopInfo(pDTO)).orElseGet(ShopDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".updateShopInfo End!");
        return "/trader/updateShopInfo";
    }

    // 상인 정보 등록페이지 이동코드
    // 구현완료(11/13)
    @GetMapping(value = "trader/insertShopInfo")
    public String insertShopInfo(HttpSession session, ModelMap model, HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".insertShopInfo start!");

        String url = "";

        String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        if (traderId.equals(null)) {
            url = "/trader/login";
        }

        TraderDTO pDTO = new TraderDTO();
        pDTO.setTraderId(traderId);
        TraderDTO tDTO = Optional.ofNullable(traderService.getTraderInfo(pDTO)).orElseGet(TraderDTO::new);

        MarketDTO pDTO2 = new MarketDTO();
        pDTO2.setMarketNumber(tDTO.getShopCode());
        log.info(tDTO.getShopCode());

        MarketDTO mDTO = Optional.ofNullable(marketService.getMarket(pDTO2)).orElseGet(MarketDTO::new);

        model.addAttribute("mDTO", mDTO);

        log.info(this.getClass().getName() + ".insertShopInfo End!");

        return url;
    }

    // 상인정보 등록 로직코드
    // 구현완료(11/13)
    @ResponseBody
    @PostMapping(value = "/trader/insertShop")
    public MsgDTO insertShop(HttpServletRequest request, @RequestParam(value = "fileUpload") MultipartFile mf, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".insertShopInfo Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        ShopDTO pDTO = null;

        try {
            String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String shopName = CmmUtil.nvl(request.getParameter("shopName"));
            String traderName = CmmUtil.nvl(request.getParameter("traderName"));
            String shopDescription = CmmUtil.nvl(request.getParameter("shopDescription"));
            String marketName = CmmUtil.nvl(request.getParameter("marketName"));
            String marketNumber = CmmUtil.nvl(request.getParameter("marketNumber"));

            log.info("traderId : " + traderId);
            log.info("shopName : " + shopName);
            log.info("traderName : " + traderName);
            log.info("shopDescription : " + shopDescription);
            log.info("marketName : " + marketName);
            log.info("marketNumber : " + marketNumber);

            pDTO = new ShopDTO();

            pDTO.setTraderId(traderId);
            pDTO.setShopName(shopName);
            pDTO.setShopDescription(shopDescription);
            pDTO.setTraderName(traderName);
            pDTO.setImage("");
            pDTO.setMarketName(marketName);
            pDTO.setMarketNumber(marketNumber);


            if (!mf.isEmpty()) {
                String image = mf.getOriginalFilename();
                String fileName = image;
                String folderName = "Trader" + "/" + pDTO.getTraderId() + "/" + "Shop" + "/";
                fileService.upload(fileName, folderName, mf);
                pDTO.setImage(fileService.getFileURL(folderName, fileName));
                log.info(pDTO.getImage());
            }
            log.info(pDTO.toString());

            res = shopService.insertShopInfo(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "등록되었습니다";
            } else {
                msg = "오류로 인해 등록 실패하였습니다";
            }
        } catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".insertShop End!");
        }
        return dto;
    }

    // 상인정보 수정 로직코드
    // 구현완료(11/13)
    @ResponseBody
    @PostMapping(value = "/trader/updateShop")
    public MsgDTO updateShop(HttpServletRequest request, @RequestParam(value = "fileUpload") MultipartFile mf, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".updateShopInfo Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        ShopDTO pDTO = null;

        try {
            String traderId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String originImage = CmmUtil.nvl((String) session.getAttribute("image"));
            String shopName = CmmUtil.nvl(request.getParameter("shopName"));
            String traderName = CmmUtil.nvl(request.getParameter("traderName"));
            String shopDescription = CmmUtil.nvl(request.getParameter("shopDescription"));

            log.info("traderId : " + traderId);
            log.info("shopName : " + shopName);
            log.info("traderName : " + traderName);
            log.info("shopDescription : " + shopDescription);
            log.info("image : " + originImage);

            pDTO = new ShopDTO();

            pDTO.setTraderId(traderId);
            pDTO.setShopName(shopName);
            pDTO.setShopDescription(shopDescription);
            pDTO.setTraderName(traderName);

            if (!mf.isEmpty()) {
                String image = mf.getOriginalFilename();
                String fileName = image;
                String folderName = "Trader" + "/" + pDTO.getTraderId() + "/" + "Shop" + "/";
                fileService.upload(fileName, folderName, mf);
                pDTO.setImage(fileService.getFileURL(folderName, fileName));
                log.info(pDTO.getImage());
            } else {
                pDTO.setImage(originImage);
            }

            log.info(pDTO.toString());

            res = shopService.updateShopInfo(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "수정되었습니다";
            } else {
                msg = "오류로 인해 수정 실패하였습니다";
            }
        } catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".updateShop End!");
        }
        return dto;
    }
}
