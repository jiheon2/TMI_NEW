package kopo.poly.controller;


import kopo.poly.dto.*;
import kopo.poly.service.IFileService;
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
public class ShopController {

    private final IShopService shopService;
    private final IFileService fileService;
    @GetMapping(value = "/trader/shopInfo")
    public String shopInfo(HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".shopInfo Start!");

        String type = (String) session.getAttribute("SS_TYPE");
        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        String url = "/trader/shopInfo";
        if(!type.equals("Trader")) {
            session.invalidate();
            url = "/trader/login";
        }
        ShopDTO pDTO = new ShopDTO();
        pDTO.setTraderId(id);
        ShopDTO rDTO = Optional.ofNullable(shopService.getShopInfo(pDTO)).orElseGet(ShopDTO::new);
        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".shopInfo End!");

        return url;
    }

    @GetMapping(value = "/trader/updateShopInfo")
    public String updateShopInfo(HttpSession session, ModelMap model, HttpServletRequest request) throws Exception{
        log.info(this.getClass().getName() + ".updateShopInfo start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        log.info(id);

        ShopDTO pDTO = new ShopDTO();

        pDTO.setTraderId(id);

        log.info(pDTO.toString());
        ShopDTO rDTO = Optional.ofNullable(shopService.getShopInfo(pDTO)).orElseGet(ShopDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".updateShopInfo start!");
        return "/trader/updateShopInfo";
    }
    @ResponseBody
    @PostMapping(value = "/trader/changeShop")
    public MsgDTO insertOrUpdateShop(HttpServletRequest request,@RequestParam (value = "fileUpload") MultipartFile mf, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".updateShop Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        ShopDTO pDTO = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String cname = CmmUtil.nvl(request.getParameter("cname"));
            String name = CmmUtil.nvl(request.getParameter("name"));
            String des = CmmUtil.nvl(request.getParameter("des"));

            log.info("id : " + id);
            log.info("cname : " + cname);


            pDTO = new ShopDTO();

            pDTO.setTraderId(id);
            pDTO.setTraderId(cname);
            pDTO.setShopDescription(des);
            pDTO.setShopName(name);
            pDTO.setImage("");



            if(!mf.isEmpty()) {
                String image = mf.getOriginalFilename();
                String fileName = image;
                String folderName = "Trader" + "/" + pDTO.getTraderId() + "/" + "Shop" + "/";
                fileService.upload(fileName, folderName , mf);
                pDTO.setImage(fileService.getFileURL(folderName, fileName));
                log.info(pDTO.getImage());
            }
            log.info(pDTO.toString());

            res = shopService.insertOrUpdateShop(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "수정하였습니다";
            } else {
                msg = "오류로 인해 수정 실패하였습니다";
            }
        }catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".updateShop End!");
        }
        return dto;
    }


//    @GetMapping(value = "/trader/goodsBuyInfo")
//    public String goodsBuyInfo(ModelMap model, HttpSession session) throws Exception {
//
//        log.info(this.getClass().getName() + ".goodsBuyInfo Start!");
//
//        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
//
//        ReserveDTO pDTO = new ReserveDTO();
//        pDTO.setTraderId(id);
//        pDTO.setState("0");
//        List<ReserveDTO> rList = shopService.goodsBuyInfo(pDTO);
//        if (rList == null) rList = new ArrayList<>();
//
//        model.addAttribute("rList", rList);
//
//        log.info(this.getClass().getName() + ".goodsBuyInfo End!");
//
//        return "/trader/goodsBuyInfo";
//    }

//    @ResponseBody
//    @PostMapping(value = "/trader/acceptBuy")
//    public MsgDTO acceptBuy(HttpSession session,@RequestBody Map<String, Object> requestData) {
//        log.info(this.getClass().getName() + ".acceptBuy Start!");
//
//        String msg = "";
//        int res = 0;
//        MsgDTO dto = null;
//
//        try {
//            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
//            String want = (String) requestData.get("want");
//            List<String> checkboxes = (List<String>) requestData.get("selectedSeqs");
//            log.info(want);
//            log.info("id : " + id);
//            log.info("checkboxes : " + checkboxes);
//            ReserveDTO pDTO = new ReserveDTO();
//            if (want.equals("delete")) {
//                for (String seq : checkboxes) {
//                    pDTO.setSeq(seq);
//                    pDTO.setTraderId(id);
//
//                    shopService.deleteBuy(pDTO);
//                }
//                msg = "삭제되었습니다.";
//            } else {
//                for (String seq : checkboxes) {
//                    pDTO.setSeq(seq);
//                    pDTO.setTraderId(id);
//
//                    shopService.acceptBuy(pDTO);
//                }
//                msg = "수락하였습니다.";
//            }
//
//            res = 1;
//        } catch (Exception e) {
//            msg = "실패하였습니다. : " + e.getMessage();
//            log.info(e.toString());
//            e.printStackTrace();
//        } finally {
//            dto = new MsgDTO();
//            dto.setResult(res);
//            dto.setMsg(msg);
//            log.info(this.getClass().getName() + ".deleteBuy End!");
//        }
//
//        return dto;
//    }

}
