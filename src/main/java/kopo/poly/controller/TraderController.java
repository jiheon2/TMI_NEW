package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping(value = "/trader")
public class TraderController {
    @RequestMapping(value = "/traderIndex")
    public String traderIndex() {
        log.info("start");

        return "/trader/traderIndex";
    }

    @RequestMapping(value = "/shopInfo")
    public String shopInfo() {
        log.info("start");

        return "/trader/shopInfo";
    }

    @RequestMapping(value = "/traderInfo")
    public String traderInfo() {
        log.info("start");

        return "/trader/traderInfo";
    }

    @RequestMapping(value = "/goodsMng")
    public String goodsMng() {
        log.info("start");

        return "/trader/goodsMng";
    }

    @RequestMapping(value = "/reservMng")
    public String reservMng() {
        log.info("start");

        return "/trader/reservMng";
    }
    @RequestMapping(value = "/reviewMng")
    public String reviewMng() {
        log.info("start");

        return "/trader/reviewMng";
    }

    @RequestMapping(value = "/updateShopInfo")
    public String updateShopInfo() {
        log.info("start");

        return "/trader/updateShopInfo";
    }

    @RequestMapping(value = "/updateTraderInfo")
    public String updateTraderInfo() {
        log.info("start");

        return "/trader/updateTraderInfo";
    }
}
