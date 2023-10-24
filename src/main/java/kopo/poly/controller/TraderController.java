package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping(value = "/trader")
public class TraderController {
    @GetMapping(value = "/traderIndex")
    public String traderIndex() {
        log.info("start");

        return "/trader/traderIndex";
    }

    @GetMapping(value = "/shopInfo")
    public String shopInfo() {
        log.info("start");

        return "/trader/shopInfo";
    }

    @GetMapping(value = "/traderInfo")
    public String traderInfo() {
        log.info("start");

        return "/trader/traderInfo";
    }

    @GetMapping(value = "/goodsMng")
    public String goodsMng() {
        log.info("start");

        return "/trader/goodsMng";
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

    @GetMapping(value = "/updateShopInfo")
    public String updateShopInfo() {
        log.info("start");

        return "/trader/updateShopInfo";
    }

    @GetMapping(value = "/updateTraderInfo")
    public String updateTraderInfo() {
        log.info("start");

        return "/trader/updateTraderInfo";
    }

    @GetMapping(value = "/channel")
    public String channel() {
        log.info("start");

        return "/trader/channel";
    }
}
