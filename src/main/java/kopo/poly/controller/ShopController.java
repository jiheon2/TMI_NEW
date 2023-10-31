package kopo.poly.controller;


import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.ProductDTO;
import kopo.poly.dto.ShopDTO;
import kopo.poly.service.IFileService;
import kopo.poly.service.IShopService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
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
        pDTO.setTid(id);
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

        pDTO.setTid(id);

        log.info(pDTO.toString());
        ShopDTO rDTO = Optional.ofNullable(shopService.getShopInfo(pDTO)).orElseGet(ShopDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".updateShopInfo start!");
        return "/trader/updateShopInfo";
    }
    @ResponseBody
    @PostMapping(value = "/trader/changeShop")
    public MsgDTO changeShop(HttpServletRequest request,@RequestParam (value = "fileUpload") MultipartFile mf, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".changeShop Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        ShopDTO pDTO = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String cname = CmmUtil.nvl(request.getParameter("cname"));
            String name = CmmUtil.nvl(request.getParameter("name"));
            String type = CmmUtil.nvl(request.getParameter("type"));
            String loc = CmmUtil.nvl(request.getParameter("loc"));
            String des = CmmUtil.nvl(request.getParameter("des"));

            log.info("name:" + name);
            log.info("cname:" + cname);
            log.info("type : " + type);
            log.info("loc : " + loc);
            log.info("des : " + des);


            pDTO = new ShopDTO();

            pDTO.setTid(id);
            pDTO.setCname(cname);
            pDTO.setType(type);
            pDTO.setLoc(loc);
            pDTO.setDes(des);
            pDTO.setName(name);
            pDTO.setImage("");



            if(!mf.isEmpty()) {
                String image = mf.getOriginalFilename();
                String fileName = image;
                String folderName = "Trader" + "/" + pDTO.getTid() + "/" + "Shop" + "/";
                fileService.upload(fileName, folderName , mf);
                pDTO.setImage(fileService.getFileURL(folderName, fileName));
                log.info(pDTO.getImage());
            }
            log.info(pDTO.toString());

            res = shopService.changeShop(pDTO);

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
            log.info(this.getClass().getName() + ".changeShop End!");
        }
        return dto;
    }
    @GetMapping(value = "/trader/goodsMng")
    public String goodsMng(ModelMap model, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + ".goodsMng Start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        ProductDTO pDTO = new ProductDTO();
        pDTO.setPid(id);
        List<ProductDTO> rList = shopService.getGoodsList(pDTO);
        if (rList == null) rList = new ArrayList<>();

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".goodsMng End!");

        return "/trader/goodsMng";
    }
    @GetMapping(value = "/trader/goodsMngInfo")
    public String goodsMngInfo(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".goodsMngInfo Start!");

        String type = (String) session.getAttribute("SS_TYPE");
        String pid = CmmUtil.nvl(request.getParameter("nPid"));
        String seq = CmmUtil.nvl(request.getParameter("nSeq"));
        String url = "/trader/goodsMngInfo";

        log.info(pid + seq);

        if(!type.equals("Trader")) {
            session.invalidate();
            url = "/trader/login";
        }
        ProductDTO pDTO = new ProductDTO();
        pDTO.setPid(pid);
        pDTO.setSeq(seq);
        ProductDTO rDTO = Optional.ofNullable(shopService.getGoodsInfo(pDTO)).orElseGet(ProductDTO::new);
        model.addAttribute("rDTO", rDTO);
        log.info(rDTO.toString());

        log.info(this.getClass().getName() + ".goodsMngInfo End!");

        return url;
    }
    @GetMapping(value = "/trader/goodsMngInsert")
    public String goodsMngInsert(HttpSession session, ModelMap model, HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".goodsMngInsert start!");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
        String seq = CmmUtil.nvl(request.getParameter("nSeq"));
        log.info(id);

        ProductDTO pDTO = new ProductDTO();

        pDTO.setPid(id);
        pDTO.setSeq(seq);

        log.info(pDTO.toString());
        ProductDTO rDTO = Optional.ofNullable(shopService.getGoodsInfo(pDTO)).orElseGet(ProductDTO::new);

        log.info(rDTO.toString());
        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".goodsMngInsert start!");
        return "/trader/goodsMngInsert";
    }
    @ResponseBody
    @PostMapping(value = "/trader/changeGoods")
    public MsgDTO changeGoods(HttpServletRequest request,@RequestParam (value = "fileUpload") MultipartFile mf, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".changeGoods Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        ProductDTO pDTO = null;

        try {
            String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String name = CmmUtil.nvl(request.getParameter("name"));
            String price = CmmUtil.nvl(request.getParameter("price"));
            String des = CmmUtil.nvl(request.getParameter("des"));
            String type = CmmUtil.nvl(request.getParameter("type"));
            String seq = CmmUtil.nvl(request.getParameter("seq"));

            log.info("price:" + price);
            log.info("des:" + des);
            log.info("name : " + name);
            log.info("type : " + type);
            log.info("seq : " + seq);

            pDTO = new ProductDTO();

            pDTO.setPrice(price);
            pDTO.setDes(des);
            pDTO.setType(type);
            pDTO.setPid(id);
            pDTO.setName(name);
            pDTO.setSeq(seq);
            pDTO.setImage("");



            if(!mf.isEmpty()) {
                String image = mf.getOriginalFilename();
                String fileName = image;
                String folderName = "Trader" + "/" + pDTO.getPid() + "/" + "Goods" + "/";
                fileService.upload(fileName, folderName , mf);
                pDTO.setImage(fileService.getFileURL(folderName, fileName));
                log.info(pDTO.getImage());
            }
            log.info(pDTO.toString());

            res = shopService.changeGoods(pDTO);

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
            log.info(this.getClass().getName() + ".changeGoods End!");
        }
        return dto;
    }
    @ResponseBody
    @PostMapping(value = "/trader/goodsMsgDelete")
    public MsgDTO goodsMsgDelete(HttpSession session, HttpServletRequest request) {

        log.info(this.getClass().getName() + ".goodsMsgDelete Start!");

        String msg = "";
        int res = 0;
        MsgDTO dto = null;

        try {
            String user_id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
            String nSeq = CmmUtil.nvl(request.getParameter("seq"));

            log.info("user_id : " + user_id);
            log.info("nSeq : " + nSeq);

            ProductDTO pDTO = new ProductDTO();
            pDTO.setPid(user_id);
            pDTO.setSeq(nSeq);
            shopService.goodsMsgDelete(pDTO);

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
            log.info(this.getClass().getName() + ".goodsMsgDelete End!");
        }

        return dto;
    }

}